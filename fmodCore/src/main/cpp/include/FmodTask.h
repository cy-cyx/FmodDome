#include "fmod/fmod.hpp"

#ifndef FMOD_FMODTASK_H
#define FMOD_FMODTASK_H

namespace FMODCORE {

    // 一个单音轨的播放任务
    class FmodTask {

    public:
        jobject object;
        bool isPlay;
        bool isRelease;

        FMOD::System *system;
        FMOD::Channel *channel;

        // 当前播放的声道（如果有附加资源可以通过它来判断结束）
        FMOD::Sound *sound;

        FmodTask() {
            system = nullptr;
            sound = nullptr;
            channel = nullptr;
            isRelease = false;
            isPlay = false;
        }
    };

}

#endif //FMOD_FMODTASK_H
