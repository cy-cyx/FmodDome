#include <jni.h>
#include <string>
#include <unistd.h>
#include "FmodPlayer.h"
#include "FmodEffects.h"
#include "Log.h"
#include "fmod/fmod_errors.h"

 JavaVM *javaVm;

jlong _init(JNIEnv *env, jobject thiz) {
    auto *task = new FMODCORE::FmodPlayer();
    task->init(env, thiz);
    return (long) task;
}

void _setSoundResource(JNIEnv *env, jobject thiz, jlong p, jstring rec) {
    auto *task = (FMODCORE::FmodPlayer *) p;
    task->setSoundResource(env, thiz, rec);
}

void _setEffect(JNIEnv *env, jobject thiz, jlong p, int mode) {
    auto *task = (FMODCORE::FmodPlayer *) p;
    task->setDspEffect(env, thiz, mode);
}

void _play(JNIEnv *env, jobject thiz, jlong p) {
    auto *task = (FMODCORE::FmodPlayer *) p;
    task->play();
}

void _pause(JNIEnv *env, jobject thiz, jlong p) {
    auto *task = (FMODCORE::FmodPlayer *) p;
    task->pause();
}

void _release(JNIEnv *env, jobject thiz, jlong p) {
    auto *task = (FMODCORE::FmodPlayer *) p;
    if (nullptr == task)return;
    task->release(env, thiz);
}

int registerNativeMethods(JNIEnv *env) {

    JNINativeMethod methods[] = {
            {"initNative",              "()J",                    (void *) _init},
            {"setSoundResourcetNative", "(JLjava/lang/String;)V", (void *) _setSoundResource},
            {"setEffectNative",         "(JI)V",                  (void *) _setEffect},
            {"playNative",              "(J)V",                   (void *) _play},
            {"pauseNative",             "(J)V",                   (void *) _pause},
            {"releaseNative",           "(J)V",                   (void *) _release},
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