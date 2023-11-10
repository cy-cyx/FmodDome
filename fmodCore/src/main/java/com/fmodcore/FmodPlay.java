package com.fmodcore;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;

public class FmodPlay {

    static {
        System.loadLibrary("fmod");
        System.loadLibrary("fmodL");
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
     * 加载资源
     *
     * @param res
     */
    public void setSoundRes(String res) {
        if (isRelease) return;
        Message message = Message.obtain();
        message.what = sCodeSetRes;
        message.obj = res;
        playHandler.sendMessage(message);
    }

    /**
     * 设置dsp
     *
     * @param mode
     */
    public void setEffect(int mode) {
        if (isRelease) return;
        Message message = Message.obtain();
        message.what = sCodeSetEffect;
        message.arg1 = mode;
        playHandler.sendMessage(message);
    }

    /**
     * 播放
     */
    public void play() {
        if (isRelease) return;
        Message message = Message.obtain();
        message.what = sCodePlay;
        playHandler.sendMessage(message);
    }

    /**
     * 暂停当前音频播放
     */
    public void pause() {
        if (isRelease) return;
        playHandler.sendEmptyMessage(sCodePause);
    }

    public boolean isPlay() {
        return playing;
    }

    private boolean isRelease = false;

    public void release() {
        isRelease = true;
        playHandler.sendEmptyMessage(sCodeRelease);
        listen = null;
    }

    private final int sCodeInit = 0;
    private final int sCodeSetRes = 1;
    private final int sCodeSetEffect = 2;
    private final int sCodePlay = 3;
    private final int sCodePause = 4;
    private final int sCodeFinishCallback = 5;
    private final int sCodeRelease = 6;

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
                case sCodeSetRes: {
                    _setSoundResource((String) msg.obj);
                    break;
                }
                case sCodeSetEffect: {
                    _setEffect(msg.arg1);
                    break;
                }
                case sCodePlay: {
                    _play();
                    break;
                }
                case sCodePause: {
                    _pause();
                    break;
                }
                case sCodeFinishCallback: {
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
        pot = initNative();
    }

    private void _setSoundResource(String res) {
        setSoundResourcetNative(pot, res);
    }

    private void _setEffect(int mode) {
        setEffectNative(pot, mode);
    }

    private void _play() {
        playNative(pot);
        playing = true;
        if (listen != null) listen.onStart();
    }

    private void _pause() {
        pauseNative(pot);
        playing = false;
        if (listen != null) listen.onPause();
    }

    private void _stopByCallback() {
        playing = false;
        if (listen != null) listen.onFinish();
    }

    private void _release() {
        releaseNative(pot);
    }


    native long initNative();

    native void setSoundResourcetNative(long pot, String res);

    native void setEffectNative(long pot, int mode);

    native void playNative(long pot);

    native void pauseNative(long pot);

    native void releaseNative(long pot);

    // call native
    void playFinishCallback() {
        playHandler.sendEmptyMessage(sCodeFinishCallback);
    }

    public interface FmodPlayListen {
        void onStart();

        void onPause();

        void onFinish();
    }

}
