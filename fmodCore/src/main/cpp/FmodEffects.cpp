#include "include/FmodEffects.h"

void FMODCORE::setEffect(FMOD::System *system, FMOD::Channel *channel, int mode) {
    switch (mode) {
        case MODE_LUOLI: {
            FMOD::DSP *dsp;
            system->createDSPByType(FMOD_DSP_TYPE_PITCHSHIFT, &dsp);
            dsp->setParameterFloat(FMOD_DSP_PITCHSHIFT_PITCH, 2.5);
            channel->addDSP(0, dsp);
            break;
        }
        case MODE_DASHU: {
            FMOD::DSP *dsp;
            system->createDSPByType(FMOD_DSP_TYPE_PITCHSHIFT,&dsp);
            dsp->setParameterFloat(FMOD_DSP_PITCHSHIFT_PITCH,0.8);
            channel->addDSP(0,dsp);
            break;
        }
        case MODE_JINGSONG: {
            FMOD::DSP *dsp;
            system->createDSPByType(FMOD_DSP_TYPE_TREMOLO,&dsp);
            dsp->setParameterFloat(FMOD_DSP_TREMOLO_SKEW, 0.5);
            channel->addDSP(0,dsp);
            break;
        }
        case MODE_GAOGUAI: {
            float frequency = 0;
            channel->getFrequency(&frequency);
            frequency = frequency * 1.6;
            channel->setFrequency(frequency);
            break;
        }
        case MODE_KONGLING: {
            FMOD::DSP *dsp;
            system->createDSPByType(FMOD_DSP_TYPE_ECHO,&dsp);
            dsp->setParameterFloat(FMOD_DSP_ECHO_DELAY,300);
            dsp->setParameterFloat(FMOD_DSP_ECHO_FEEDBACK,20);
            channel->addDSP(0,dsp);
            break;
        }

        default:
            break;
    }
}