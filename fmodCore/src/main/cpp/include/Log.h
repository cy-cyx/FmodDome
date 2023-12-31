#ifndef FMOD_LOG_H
#define FMOD_LOG_H

#include <android/log.h>

#define LOGV(tag, format, ...)  __android_log_print(ANDROID_LOG_VERBOSE, tag, format, ##__VA_ARGS__)
#define LOGD(tag, format, ...)  __android_log_print(ANDROID_LOG_DEBUG, tag, format, ##__VA_ARGS__)
#define LOGI(tag, format, ...)  __android_log_print(ANDROID_LOG_INFO,  tag, format, ##__VA_ARGS__)
#define LOGW(tag, format, ...)  __android_log_print(ANDROID_LOG_WARN, tag, format, ##__VA_ARGS__)
#define LOGE(tag, format, ...)  __android_log_print(ANDROID_LOG_ERROR, tag, format, ##__VA_ARGS__)

#endif //FMOD_LOG_H
