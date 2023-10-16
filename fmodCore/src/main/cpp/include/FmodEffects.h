#include "fmod/fmod.hpp"
// 变声效果

#ifndef FMOD_FMODEFFECTS_H
#define FMOD_FMODEFFECTS_H

#define MODE_NORMAL 0       //普通声音
#define MODE_LUOLI 1        //萝莉声音
#define MODE_DASHU 2        //大叔声音
#define MODE_JINGSONG 3     //惊悚声音
#define MODE_GAOGUAI 4      //搞怪声音
#define MODE_KONGLING 5     //空灵声音

namespace FMODCORE{
    void setEffect(FMOD::System *system, FMOD::Channel *channel, int mode);
}

#endif //FMOD_FMODEFFECTS_H
