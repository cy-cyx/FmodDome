#include <jni.h>
#include <string>
#include "FmodTask.h"
#include "FmodEffects.h"

jlong _playSound(JNIEnv *env, jobject thiz, jstring rec, jint mode) {
    auto *task = new FMODCORE::FmodTask();
    try {
        FMOD::System *system;
        System_Create(&system);
        system->init(32, FMOD_INIT_NORMAL, NULL);
        task->system = system;

        FMOD::Sound *sound;
        const char *path = env->GetStringUTFChars(rec, 0);
        system->createSound(path, FMOD_DEFAULT, 0, &sound);
        sound->setLoopCount(INT16_MAX);  // 循环播放
        task->sound = sound;

        FMOD::Channel *channel;
        system->playSound(sound, 0, false, &channel);

        // 添加效果
        FMODCORE::setEffect(task->system, task->channel, mode);
    } catch (...) {

    }
    return (long) task;
}

void _pauseSound(JNIEnv *env, jobject thiz, jlong p) {
    try {
        auto *task = (FMODCORE::FmodTask *) p;
        if (nullptr != task->channel) {
            task->channel->setPaused(true);
        }
    } catch (...) {
    }
}

void _replaySound(JNIEnv *env, jobject thiz, jlong p) {
    try {
        auto *task = (FMODCORE::FmodTask *) p;
        if (nullptr != task->channel) {
            task->channel->setPaused(false);
        }
    } catch (...) {
    }
}

void _release(JNIEnv *env, jobject thiz, jlong p) {
    try {
        auto *task = (FMODCORE::FmodTask *) p;
        if (nullptr != task->sound) {
            task->sound->release();
        }
        if (nullptr != task->system) {
            task->system->close();
            task->system->release();
        }
    } catch (...) {
    }
}

int registerNativeMethods(JNIEnv *env) {

    JNINativeMethod methods[] = {
            {"playSound",   "(Ljava/lang/String;I)J", (void *) _playSound},
            {"pauseSound",  "(J)V",                   (void *) _pauseSound},
            {"replaySound", "(J)V",                   (void *) _replaySound},
            {"release",     "(J)V",                   (void *) _release},
    };

    const char *className = "com/fmodcore/FmodCore";

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

    if (env == NULL) {
        return -1;
    }

    // 动态注册
    if (!registerNativeMethods(env)) {
        return -1;
    }

    return JNI_VERSION_1_6;
}