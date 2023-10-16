#include "fmod/fmod.hpp"

#ifndef FMOD_FMODTASK_H
#define FMOD_FMODTASK_H

namespace FMODCORE {

    // 一个单音轨的播放任务
    class FmodTask {

    public:
        FMOD::System *system;
        FMOD::Sound *sound;
        FMOD::Channel *channel;

        FmodTask() {
            system = nullptr;
            sound = nullptr;
            channel = nullptr;
        }
    };

}

#endif //FMOD_FMODTASK_H
