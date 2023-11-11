#include "FmodPlayer.h"
#include "fmod/fmod_errors.h"

// 循环检查结束
void *checkChannelState(void *arg) {
    auto *task = (FMODCORE::FmodPlayer *) arg;

    JNIEnv *jniEnv;

    if (javaVm != NULL) {
        javaVm->AttachCurrentThread(&jniEnv, NULL);
    }

    while (!task->isRelease) {
        try {
            // 检查是否播放完成的回调
            if (nullptr != task->channel && !task->isFinish) {
                bool isPlay;
                task->channel->isPlaying(&isPlay);

                if (isPlay) {
                    LOGV("fmod", "isPlay = true");
                } else {
                    LOGV("fmod", "isPlay = false");
                }

                if (!isPlay) {
                    task->isFinish = true;

                    jobject object = task->object;
                    jclass clazz = jniEnv->GetObjectClass(object);
                    jmethodID method = jniEnv->GetMethodID(clazz, "playFinishCallback", "()V");
                    jniEnv->CallVoidMethod(object, method);
                }
            }
        } catch (...) {

        }

        usleep(1 * 1000 * 1000);
    }

    if (javaVm != NULL) {
        javaVm->DetachCurrentThread();
    }

    free(task);

    pthread_exit(NULL);
}

void FMODCORE::FmodPlayer::init(JNIEnv *env, jobject thiz) {
    object = (jobject) env->NewGlobalRef(thiz);

    pthread_t threadPrt;
    pthread_create(&threadPrt, NULL, checkChannelState, this);

    FMOD::System *tempSystem;
    System_Create(&tempSystem);
    system = tempSystem;
    system->init(32, FMOD_INIT_NORMAL, NULL);
}

void FMODCORE::FmodPlayer::setSoundResource(JNIEnv *env, jobject thiz, jstring rec) {
    try {
        if (nullptr != channel) {
            channel->stop();
            channel = nullptr;
        }
        if (nullptr != sound) {
            sound->release();
        }
        FMOD::Sound *tempSound;
        const char *path = env->GetStringUTFChars(rec, 0);
        FMOD_RESULT result = system->createSound(path, FMOD_DEFAULT, 0, &tempSound);
        LOGV("fmod", "createSound result: %s", FMOD_ErrorString(result));

        sound = tempSound;
        soundPath = path;
        LOGV("fmod", "resource ready!");
    } catch (...) {
    }
}

void FMODCORE::FmodPlayer::cleanEffect() {
    //  清掉久的效果
    for (int i = 0; i < dspSize; ++i) {
        FMOD::DSP *tempDsp = dsp[i];
        channel->removeDSP(tempDsp);
    }
    // 恢复一下正常速率
    channel->setFrequency(normalFrequency);
    dspSize = 0;
}

void FMODCORE::FmodPlayer::setDspEffect(JNIEnv *env, jobject thiz, int mode) {
    cleanEffect();
    try {
        dspMode = mode;

        if (nullptr != channel) {
            setEffect(system, channel, dsp, &dspSize, mode);
        }
    } catch (...) {
    }
}

void FMODCORE::FmodPlayer::play() {
    try {
        if (nullptr == sound) {
            return;
        }

        if (nullptr != channel) {
            bool isPlay;
            channel->isPlaying(&isPlay);

            // 如果播放结束重新播放
            if (!isPlay) {
                FMOD::Channel *tempChannel;
                system->playSound(sound, 0, false, &tempChannel);
                channel = tempChannel;

                // 记一下正常速率
                float frequency = 0;
                channel->getFrequency(&frequency);
                normalFrequency = frequency;

                // 把效果也设置上去
                setEffect(system, channel, dsp, &dspSize, dspMode);
            } else {
                channel->setPaused(false);
            }

        } else {
            FMOD::Channel *tempChannel;
            system->playSound(sound, 0, false, &tempChannel);
            channel = tempChannel;

            // 记一下正常速率
            float frequency = 0;
            channel->getFrequency(&frequency);
            normalFrequency = frequency;

            // 把效果也设置上去
            setEffect(system, channel, dsp, &dspSize, dspMode);
        }

        isFinish = false;
    } catch (...) {

    }
}

void FMODCORE::FmodPlayer::pause() {
    try {
        if (nullptr != channel) {
            channel->setPaused(true);
        }
    } catch (...) {
    }
}

void FMODCORE::FmodPlayer::release(JNIEnv *env, jobject thiz) {
    try {
        isRelease = true;
        isFinish = true;
        if (nullptr != sound) {
            sound->release();
        }
        if (nullptr != system) {
            system->close();
            system->release();
        }
        env->DeleteGlobalRef(object);
    } catch (...) {
    }
}

void FMODCORE::FmodPlayer::save(JNIEnv *env, jobject thiz, jstring savePath) {
    char dest[1000];
    const char *path = env->GetStringUTFChars(savePath, 0);
    strcpy(dest, path);

    FMOD::System *saveSystem;
    System_Create(&saveSystem);
    saveSystem->setSoftwareFormat(8000, FMOD_SPEAKERMODE_MONO, 0);
    saveSystem->setOutput(FMOD_OUTPUTTYPE_WAVWRITER);
    saveSystem->init(2, FMOD_INIT_NORMAL, dest);

    FMOD::Sound *saveSound;
    saveSystem->createSound(soundPath, FMOD_DEFAULT, 0, &saveSound);

    FMOD::Channel *saveChannel;
    saveSystem->playSound(saveSound, 0, false, &saveChannel);

    FMOD::DSP *saveDsp[5];
    int saveDspSize;
    setEffect(saveSystem, saveChannel, saveDsp, &saveDspSize, dspMode);

    bool playing = true;
    while (playing) {
        usleep(1000);
        saveChannel->isPlaying(&playing);
    }

    saveSound->release();
    saveSystem->close();
    saveSystem->release();
}