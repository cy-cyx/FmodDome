package com.fmodcore;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;

public class FmodPlay {

    static {
        System.loadLibrary("fmodL");
        System.loadLibrary("fmod");
        System.loadLibrary("fmodcore");
    }

    private PlayHandler playHandler;

    public FmodPlay() {
        HandlerThread thread = new HandlerThread("FmodPlay");
        thread.start();
        playHandler = new PlayHandler(thread.getLooper());

        playHandler.sendEmptyMessage(sCodeInit);
    }

    private FmodPlayListen listen;

    public void setListen(FmodPlayListen listen) {
        this.listen = listen;
    }

    /**
     * 从头播放使用指定效果播放一段音频
     *
     * @param res  本地音频文件
     * @param mode 效果
     */
    public void play(String res, int mode) {
        if (isRelease) return;
        Message message = Message.obtain();
        message.what = sCodePlay;
        message.obj = res;
        message.arg1 = mode;
        playHandler.sendMessage(message);
    }

    /**
     * 停止当前音频播放
     */
    public void stop() {
        if (isRelease) return;
        playHandler.sendEmptyMessage(sCodeStop);
    }

    private boolean isRelease = false;

    public void release() {
        isRelease = true;
        playHandler.sendEmptyMessage(sCodeRelease);
        listen = null;
    }

    private final int sCodeInit = 0;
    private final int sCodePlay = 1;
    private final int sCodeStop = 2;
    private final int sCodeStopCallback = 3;
    private final int sCodeRelease = 4;

    class PlayHandler extends Handler {

        public PlayHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case sCodeInit: {
                    _init();
                    break;
                }
                case sCodePlay: {
                    _play((String) msg.obj, msg.arg1);
                    break;
                }
                case sCodeStop: {
                    _stop();
                    break;
                }
                case sCodeStopCallback: {
                    _stopByCallback();
                    break;
                }
                case sCodeRelease: {
                    _release();
                    Looper.myLooper().quitSafely();
                    break;
                }
            }
        }
    }

    private long pot = -1;
    private boolean playing = false;

    private void _init() {
        pot = initNatvie();
    }

    private void _play(String res, int mode) {
        playNavite(pot, res, mode);
        playing = true;
        if (listen != null) listen.onStart();
    }

    private void _stop() {
        stopNative(pot);
        playing = false;
        if (listen != null) listen.onStop();
    }

    private void _stopByCallback() {
        playing = false;
        if (listen != null) listen.onStop();
    }

    private void _release() {
        releaseNative(pot);
    }


    native long initNatvie();

    native void playNavite(long pot, String res, int mode);

    native void stopNative(long pot);

    native void releaseNative(long pot);

    // call native
    void playStopCallback() {
        playHandler.sendEmptyMessage(sCodeStopCallback);
    }

    interface FmodPlayListen {
        void onStart();

        void onStop();
    }

}
