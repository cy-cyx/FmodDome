#include <jni.h>
#include <string>
#include <unistd.h>
#include "FmodTask.h"
#include "FmodEffects.h"
#include "Log.h"
#include "fmod/fmod_errors.h"

JavaVM *javaVm;

// 循环检查结束
void *checkChannelState(void *arg) {
    auto *task = (FMODCORE::FmodTask *) arg;

    JNIEnv *jniEnv;

    if (javaVm != NULL) {
        javaVm->AttachCurrentThread(&jniEnv, NULL);
    }

    while (!task->isRelease) {
        try {
            if (nullptr != task->channel && task->isPlay) {
                bool pause;
                task->channel->getPaused(&pause);

                usleep(3 * 1000 * 1000);
                if (!pause) {
                    task->isPlay = false;

                    jobject object = task->object;
                    jclass clazz = jniEnv->GetObjectClass(object);
                    jmethodID method = jniEnv->GetMethodID(clazz, "playStopCallback", "()V");
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

jlong _init(JNIEnv *env, jobject thiz) {
    auto *task = new FMODCORE::FmodTask();

    task->object = (jobject) env->NewGlobalRef(thiz);

    pthread_t threadPrt;
    pthread_create(&threadPrt, NULL, checkChannelState, task);

    FMOD::System *system;
    System_Create(&system);
    system->init(32, FMOD_INIT_NORMAL, NULL);
    task->system = system;

    return (long) task;
}

void _play(JNIEnv *env, jobject thiz, jlong p, jstring rec, jint mode) {
    auto *task = (FMODCORE::FmodTask *) p;

    try {
        FMOD::System *system = task->system;

        if (nullptr != task->sound) {
            task->sound->release();
        }
        FMOD::Sound *sound;
        const char *path = env->GetStringUTFChars(rec, 0);
        system->createSound(path, FMOD_DEFAULT, 0, &sound);
        task->sound = sound;

        if (nullptr != task->channel) {
            task->channel->setPaused(false);
        }
        FMOD::Channel *channel;
        system->playSound(sound, 0, false, &channel);
        task->channel = channel;

        // 添加效果
        FMODCORE::setEffect(task->system, task->channel, mode);

        task->isPlay = true;
    } catch (...) {

    }
}

void _stop(JNIEnv *env, jobject thiz, jlong p) {
    auto *task = (FMODCORE::FmodTask *) p;
    try {
        task->isPlay = false;

        if (nullptr != task->channel) {
            task->channel->setPaused(true);
        }
    } catch (...) {

    }
}

void _release(JNIEnv *env, jobject thiz, jlong p) {
    try {
        auto *task = (FMODCORE::FmodTask *) p;
        if (nullptr == task)return;
        task->isRelease = true;
        task->isPlay = false;
        if (nullptr != task->sound) {
            task->sound->release();
        }
        if (nullptr != task->system) {
            task->system->close();
            task->system->release();
        }
        env->DeleteGlobalRef(task->object);
    } catch (...) {
    }
}

int registerNativeMethods(JNIEnv *env) {

    JNINativeMethod methods[] = {
            {"initNatvie",    "()J",                     (void *) _init},
            {"playNavite",    "(JLjava/lang/String;I)V", (void *) _play},
            {"stopNative",    "(J)V",                    (void *) _stop},
            {"releaseNative", "(J)V",                    (void *) _release},
    };

    const char *className = "com/fmodcore/FmodPlay";

    jclass clazz;

    clazz = env->FindClass(className);
    if (clazz == NULL) {
        return JNI_FALSE;
    }

    if (env->RegisterNatives(clazz, methods, sizeof(methods) / sizeof(methods[0])) < 0) {
        return JNI_FALSE;
    }
    return JNI_TRUE;
}

// System.loadLibrary 调用后的回调
JNIEXPORT jint JNICALL JNI_OnLoad(JavaVM *vm, void *reserved) {
    JNIEnv *env = NULL;

    if (vm->GetEnv(reinterpret_cast<void **>(&env), JNI_VERSION_1_6) != JNI_OK) {
        return -1;
    }

    javaVm = vm;

    if (env == NULL) {
        return -1;
    }

    // 动态注册
    if (!registerNativeMethods(env)) {
        return -1;
    }

    return JNI_VERSION_1_6;
}