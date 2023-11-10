#include "include/FmodEffects.h"

void FMODCORE::FmodPlayer::setEffect(int mode) {
    //  清掉久的效果
    for (int i = 0; i < dspSize; ++i) {
        FMOD::DSP *tempDsp = dsp[i];
        channel->removeDSP(tempDsp);
    }
    // 恢复一下正常速率
    channel->setFrequency(normalFrequency);
    dspSize = 0;

    switch (mode) {
        case MODE_LUOLI: {
            FMOD::DSP *tempDsp;
            system->createDSPByType(FMOD_DSP_TYPE_PITCHSHIFT, &tempDsp);
            tempDsp->setParameterFloat(FMOD_DSP_PITCHSHIFT_PITCH, 2.5);
            channel->addDSP(0, tempDsp);
            dsp[0] = tempDsp;

            dspSize = 1;
            break;
        }
        case MODE_DASHU: {
            FMOD::DSP *tempDsp;
            system->createDSPByType(FMOD_DSP_TYPE_PITCHSHIFT, &tempDsp);
            tempDsp->setParameterFloat(FMOD_DSP_PITCHSHIFT_PITCH, 0.8);
            channel->addDSP(0, tempDsp);
            dsp[0] = tempDsp;

            dspSize = 1;
            break;
        }
        case MODE_JINGSONG: {
            FMOD::DSP *tempDsp;
            system->createDSPByType(FMOD_DSP_TYPE_TREMOLO, &tempDsp);
            tempDsp->setParameterFloat(FMOD_DSP_TREMOLO_SKEW, 0.5);
            channel->addDSP(0, tempDsp);
            dsp[0] = tempDsp;

            dspSize = 1;
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
            FMOD::DSP *tempDsp;
            system->createDSPByType(FMOD_DSP_TYPE_ECHO, &tempDsp);
            tempDsp->setParameterFloat(FMOD_DSP_ECHO_DELAY, 300);
            tempDsp->setParameterFloat(FMOD_DSP_ECHO_FEEDBACK, 20);
            channel->addDSP(0, tempDsp);
            dsp[0] = tempDsp;

            dspSize = 1;
            break;
        }
        case MODE_ZOMBIE: {
            FMOD::DSP *tempDsp;
            system->createDSPByType(FMOD_DSP_TYPE_FLANGE, &tempDsp);
            tempDsp->setParameterFloat(FMOD_DSP_FLANGE_MIX, 85.0);
            tempDsp->setParameterFloat(FMOD_DSP_FLANGE_DEPTH, 0.5);
            tempDsp->setParameterFloat(FMOD_DSP_FLANGE_RATE, 3.5);
            channel->addDSP(0, tempDsp);
            dsp[0] = tempDsp;

            FMOD::DSP *tempDsp1;
            system->createDSPByType(FMOD_DSP_TYPE_PITCHSHIFT, &tempDsp1);
            tempDsp1->setParameterFloat(FMOD_DSP_PITCHSHIFT_PITCH, 0.85);
            tempDsp1->setParameterFloat(FMOD_DSP_PITCHSHIFT_FFTSIZE, 1024);
            tempDsp1->setParameterFloat(FMOD_DSP_PITCHSHIFT_OVERLAP, 0);
            channel->addDSP(1, tempDsp1);
            dsp[1] = tempDsp1;

            dspSize = 2;
            break;
        }
        case MODE_FAN: {
            FMOD::DSP *tempDsp;
            system->createDSPByType(FMOD_DSP_TYPE_TREMOLO, &tempDsp);
            tempDsp->setParameterFloat(FMOD_DSP_TREMOLO_FREQUENCY, 5.0);
            tempDsp->setParameterFloat(FMOD_DSP_TREMOLO_DEPTH, 1.0);
            tempDsp->setParameterFloat(FMOD_DSP_TREMOLO_SHAPE, 0.0);
            tempDsp->setParameterFloat(FMOD_DSP_TREMOLO_SKEW, 0.0);
            tempDsp->setParameterFloat(FMOD_DSP_TREMOLO_DUTY, 0.5);
            tempDsp->setParameterFloat(FMOD_DSP_TREMOLO_SQUARE, 0.5);
            tempDsp->setParameterFloat(FMOD_DSP_TREMOLO_PHASE, 0.5);
            tempDsp->setParameterFloat(FMOD_DSP_TREMOLO_SPREAD, 2.0);
            channel->addDSP(0, tempDsp);
            dsp[0] = tempDsp;

            dspSize = 1;
            break;
        }
        case MODE_KARAOKE: {
            FMOD::DSP *tempDsp;
            system->createDSPByType(FMOD_DSP_TYPE_THREE_EQ, &tempDsp);
            tempDsp->setParameterFloat(FMOD_DSP_THREE_EQ_LOWGAIN, 0.0);
            tempDsp->setParameterFloat(FMOD_DSP_THREE_EQ_MIDGAIN, 0.0);
            tempDsp->setParameterFloat(FMOD_DSP_THREE_EQ_HIGHGAIN, 0.0);
            tempDsp->setParameterFloat(FMOD_DSP_THREE_EQ_LOWCROSSOVER, 100.0);
            tempDsp->setParameterFloat(FMOD_DSP_THREE_EQ_HIGHCROSSOVER, 4000.0);
            tempDsp->setParameterFloat(FMOD_DSP_THREE_EQ_CROSSOVERSLOPE, 1.0);
            channel->addDSP(0, tempDsp);
            dsp[0] = tempDsp;

            FMOD::DSP *tempDsp1;
            system->createDSPByType(FMOD_DSP_TYPE_DELAY, &tempDsp1);
            tempDsp1->setParameterFloat(FMOD_DSP_DELAY_CH0, 4000.0);
            tempDsp1->setParameterFloat(FMOD_DSP_DELAY_CH1, 20.0);
            tempDsp1->setParameterFloat(FMOD_DSP_DELAY_CH2, 40.0);
            tempDsp1->setParameterFloat(FMOD_DSP_DELAY_CH3, 5000.0);
            tempDsp1->setParameterFloat(FMOD_DSP_DELAY_CH4, 50.0);
            tempDsp1->setParameterFloat(FMOD_DSP_DELAY_CH5, 100.0);
            tempDsp1->setParameterFloat(FMOD_DSP_DELAY_CH6, 100.0);
            tempDsp1->setParameterFloat(FMOD_DSP_DELAY_CH7, 250.0);
            tempDsp1->setParameterFloat(FMOD_DSP_DELAY_CH8, 0.0);
            tempDsp1->setParameterFloat(FMOD_DSP_DELAY_CH9, 20000.0);
            tempDsp1->setParameterFloat(FMOD_DSP_DELAY_CH10, 50.0);
            channel->addDSP(1, tempDsp1);
            dsp[1] = tempDsp1;

            dspSize = 2;
            break;
        }
        case MODE_BASSBOOSTER: {
            FMOD::DSP *tempDsp;
            system->createDSPByType(FMOD_DSP_TYPE_THREE_EQ, &tempDsp);
            tempDsp->setParameterFloat(FMOD_DSP_THREE_EQ_LOWGAIN, 10.0);
            tempDsp->setParameterFloat(FMOD_DSP_THREE_EQ_MIDGAIN, 10.0);
            tempDsp->setParameterFloat(FMOD_DSP_THREE_EQ_HIGHGAIN, 0.0);
            tempDsp->setParameterFloat(FMOD_DSP_THREE_EQ_LOWCROSSOVER, 500.0);
            tempDsp->setParameterFloat(FMOD_DSP_THREE_EQ_HIGHCROSSOVER, 4000.0);
            tempDsp->setParameterFloat(FMOD_DSP_THREE_EQ_CROSSOVERSLOPE, 1.0);
            channel->addDSP(0, tempDsp);
            dsp[0] = tempDsp;

            dspSize = 1;
            break;
        }
        case MODE_ROBOT: {
            FMOD::DSP *tempDsp;
            system->createDSPByType(FMOD_DSP_TYPE_ECHO, &tempDsp);
            tempDsp->setParameterFloat(FMOD_DSP_ECHO_DELAY, 10.0);
            tempDsp->setParameterFloat(FMOD_DSP_ECHO_DRYLEVEL, -80.0);
            tempDsp->setParameterFloat(FMOD_DSP_ECHO_WETLEVEL, 2.0);
            channel->addDSP(0, tempDsp);
            dsp[0] = tempDsp;

            dspSize = 1;
            break;
        }
        case UNDER_WATER: {
            FMOD::DSP *tempDsp;
            system->createDSPByType(FMOD_DSP_TYPE_SFXREVERB, &tempDsp);
            tempDsp->setParameterFloat(FMOD_DSP_SFXREVERB_DECAYTIME, 1500.0);
            tempDsp->setParameterFloat(FMOD_DSP_SFXREVERB_EARLYDELAY, 20.0);
            tempDsp->setParameterFloat(FMOD_DSP_SFXREVERB_LATEDELAY, 40.0);
            tempDsp->setParameterFloat(FMOD_DSP_SFXREVERB_HFREFERENCE, 5000.0);
            tempDsp->setParameterFloat(FMOD_DSP_SFXREVERB_HFDECAYRATIO, 50.0);
            tempDsp->setParameterFloat(FMOD_DSP_SFXREVERB_DIFFUSION, 100.0);
            tempDsp->setParameterFloat(FMOD_DSP_SFXREVERB_DENSITY, 100.0);
            tempDsp->setParameterFloat(FMOD_DSP_SFXREVERB_LOWSHELFFREQUENCY, 250.0);
            tempDsp->setParameterFloat(FMOD_DSP_SFXREVERB_LOWSHELFGAIN, 0.0);
            tempDsp->setParameterFloat(FMOD_DSP_SFXREVERB_HIGHCUT, 1500.0);
            tempDsp->setParameterFloat(FMOD_DSP_SFXREVERB_EARLYLATEMIX, 50.0);
            tempDsp->setParameterFloat(FMOD_DSP_SFXREVERB_WETLEVEL, 1.0);
            tempDsp->setParameterFloat(FMOD_DSP_SFXREVERB_DRYLEVEL, 1.0);
            channel->addDSP(0, tempDsp);
            dsp[0] = tempDsp;

            dspSize = 1;
            break;
        }
        case DARK: {
            FMOD::DSP *tempDsp;
            system->createDSPByType(FMOD_DSP_TYPE_DELAY, &tempDsp);
            tempDsp->setParameterFloat(FMOD_DSP_DELAY_CH0, 10.0);
            tempDsp->setParameterFloat(FMOD_DSP_DELAY_CH1, 70.0);
            channel->addDSP(0, tempDsp);
            dsp[0] = tempDsp;

            FMOD::DSP *tempDsp1;
            system->createDSPByType(FMOD_DSP_TYPE_CHORUS, &tempDsp1);
            tempDsp1->setParameterFloat(FMOD_DSP_CHORUS_MIX, 100.0);
            tempDsp1->setParameterFloat(FMOD_DSP_CHORUS_RATE, 5.0);
            tempDsp1->setParameterFloat(FMOD_DSP_CHORUS_DEPTH, 20.0);
            channel->addDSP(1, tempDsp1);
            dsp[1] = tempDsp1;

            FMOD::DSP *tempDsp2;
            system->createDSPByType(FMOD_DSP_TYPE_PITCHSHIFT, &tempDsp2);
            tempDsp2->setParameterFloat(FMOD_DSP_PITCHSHIFT_FFTSIZE, 1024.0);
            tempDsp2->setParameterFloat(FMOD_DSP_PITCHSHIFT_MAXCHANNELS, 0);
            channel->addDSP(2, tempDsp2);
            dsp[2] = tempDsp2;

            dspSize = 3;
            break;
        }
        case CHIPMUNK: {
            FMOD::DSP *tempDsp;
            system->createDSPByType(FMOD_DSP_TYPE_ENVELOPEFOLLOWER, &tempDsp);
            tempDsp->setParameterFloat(FMOD_DSP_ENVELOPEFOLLOWER_RELEASE, 10.0);
            channel->addDSP(0, tempDsp);
            dsp[0] = tempDsp;

            FMOD::DSP *tempDsp1;
            system->createDSPByType(FMOD_DSP_TYPE_PITCHSHIFT, &tempDsp1);
            tempDsp1->setParameterFloat(FMOD_DSP_PITCHSHIFT_PITCH, 3);
            channel->addDSP(1, tempDsp1);
            dsp[1] = tempDsp1;

            dspSize = 2;
            break;
        }
        case LION: {
            FMOD::DSP *tempDsp;
            system->createDSPByType(FMOD_DSP_TYPE_PITCHSHIFT, &tempDsp);
            tempDsp->setParameterFloat(FMOD_DSP_PITCHSHIFT_PITCH, 0.1);
            tempDsp->setParameterFloat(FMOD_DSP_PITCHSHIFT_FFTSIZE, 1024.0);
            tempDsp->setParameterFloat(FMOD_DSP_PITCHSHIFT_MAXCHANNELS, 0.0);
            channel->addDSP(0, tempDsp);
            dsp[0] = tempDsp;

            dspSize = 1;
            break;
        }
        case OLD_RADIO: {
            FMOD::DSP *tempDsp;
            system->createDSPByType(FMOD_DSP_TYPE_SFXREVERB, &tempDsp);
            tempDsp->setParameterFloat(FMOD_DSP_SFXREVERB_DECAYTIME, 1500.0);
            tempDsp->setParameterFloat(FMOD_DSP_SFXREVERB_EARLYDELAY, 20.0);
            tempDsp->setParameterFloat(FMOD_DSP_SFXREVERB_LATEDELAY, 40.0);
            tempDsp->setParameterFloat(FMOD_DSP_SFXREVERB_HFREFERENCE, 5000.0);
            tempDsp->setParameterFloat(FMOD_DSP_SFXREVERB_HFDECAYRATIO, 50.0);
            tempDsp->setParameterFloat(FMOD_DSP_SFXREVERB_DIFFUSION, 100.0);
            tempDsp->setParameterFloat(FMOD_DSP_SFXREVERB_DENSITY, 100.0);
            tempDsp->setParameterFloat(FMOD_DSP_SFXREVERB_LOWSHELFFREQUENCY, 250.0);
            tempDsp->setParameterFloat(FMOD_DSP_SFXREVERB_LOWSHELFGAIN, 0.0);
            tempDsp->setParameterFloat(FMOD_DSP_SFXREVERB_HIGHCUT, 20000.0);
            tempDsp->setParameterFloat(FMOD_DSP_SFXREVERB_EARLYLATEMIX, 50.0);
            tempDsp->setParameterFloat(FMOD_DSP_SFXREVERB_WETLEVEL, 1.0);
            tempDsp->setParameterFloat(FMOD_DSP_SFXREVERB_DRYLEVEL, 1.0);
            channel->addDSP(0, tempDsp);
            dsp[0] = tempDsp;

            dspSize = 1;
            break;
        }

        default:
            break;
    }
}