#include <jni.h>
#include <string>
#include <unistd.h>
#include "FmodPlayer.h"
#include "FmodEffects.h"
#include "Log.h"
#include "fmod/fmod_errors.h"

JavaVM *javaVm;

jlong _init(JNIEnv *env, jobject thiz) {
    auto *player = new FMODCORE::FmodPlayer();
    player->init(env, thiz);
    return (long) player;
}

void _setSoundResource(JNIEnv *env, jobject thiz, jlong p, jstring rec) {
    auto *player = (FMODCORE::FmodPlayer *) p;
    player->setSoundResource(env, thiz, rec);
}

void _setEffect(JNIEnv *env, jobject thiz, jlong p, int mode) {
    auto *player = (FMODCORE::FmodPlayer *) p;
    player->setDspEffect(env, thiz, mode);
}

void _play(JNIEnv *env, jobject thiz, jlong p) {
    auto *player = (FMODCORE::FmodPlayer *) p;
    player->play();
}

void _pause(JNIEnv *env, jobject thiz, jlong p) {
    auto *player = (FMODCORE::FmodPlayer *) p;
    player->pause();
}

void _release(JNIEnv *env, jobject thiz, jlong p) {
    auto *player = (FMODCORE::FmodPlayer *) p;
    if (nullptr == player)return;
    player->release(env, thiz);
}

void _save(JNIEnv *env, jobject thiz, jlong p, jstring savePath) {
    auto *player = (FMODCORE::FmodPlayer *) p;
    player->save(env, thiz, savePath);
}

int registerNativeMethods(JNIEnv *env) {

    JNINativeMethod methods[] = {
            {"initNative",              "()J",                    (void *) _init},
            {"setSoundResourcetNative", "(JLjava/lang/String;)V", (void *) _setSoundResource},
            {"setEffectNative",         "(JI)V",                  (void *) _setEffect},
            {"playNative",              "(J)V",                   (void *) _play},
            {"pauseNative",             "(J)V",                   (void *) _pause},
            {"releaseNative",           "(J)V",                   (void *) _release},
            {"saveNative",              "(JLjava/lang/String;)V", (void *) _save},
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