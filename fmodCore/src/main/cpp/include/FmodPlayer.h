#include "fmod/fmod.hpp"
#include <jni.h>
#include "Log.h"
#include <string>
#include <unistd.h>

#ifndef FMOD_FMODTASK_H
#define FMOD_FMODTASK_H

extern JavaVM *javaVm;

namespace FMODCORE {

    // 代表一个音效播放器
    class FmodPlayer {

    public:
        // 对应java类方法
        jobject object;
        bool isFinish;
        bool isRelease;

        FMOD::System *system;

        // 当前音乐文件资源
        FMOD::Sound *sound;
        const char *soundPath;

        // 当前播放的声道
        FMOD::Channel *channel;

        // 记住正常的速率
        float normalFrequency;
        float curFrequency;

        // dsp
        int dspMode;

        // 存放当前dsp
        FMOD::DSP *dsp[5];
        int dspSize;

        FmodPlayer() {
            isRelease = false;
            isFinish = false;

            system = nullptr;
            sound = nullptr;
            channel = nullptr;
            dspMode = 0;
            dspSize = 0;
        }

        void init(JNIEnv *env, jobject thiz);

        void setSoundResource(JNIEnv *env, jobject thiz, jstring rec);

        void setDspEffect(JNIEnv *env, jobject thiz, int mode);

        void cleanEffect();

        void play();

        void pause();

        void save(JNIEnv *env, jobject thiz, jstring savePath);

        void release(JNIEnv *env, jobject thiz);
    };

    void setEffect(FMOD::System *targetSystem, FMOD::Channel *targetChannel,
                   FMOD::DSP **targetDsp, int *targetDspSize,
                   int mode);
}

#endif //FMOD_FMODTASK_H
