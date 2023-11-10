package com.fmod.data

import com.fmod.R
import com.fmodcore.EffectMode

object DspEffectRepository {

    val dsp = ArrayList<DspEffect>().apply {
        add(DspEffect(0, EffectMode.NORMAL, R.string.normal, R.drawable.effect_normal))
        add(DspEffect(1, EffectMode.LUOLI, R.string.girl, R.drawable.effect_girl))
        add(DspEffect(2, EffectMode.DASHU, R.string.man, R.drawable.effect_man))
        add(DspEffect(3, EffectMode.JINGSONG, R.string.ghost, R.drawable.effect_ghost))
        add(DspEffect(4, EffectMode.GAOGUAI, R.string.alien, R.drawable.effect_alien))
        add(DspEffect(5, EffectMode.KONGLING, R.string.echo, R.drawable.effect_echo))
        add(DspEffect(6, EffectMode.ZOMBIE, R.string.zombie, R.drawable.effect_zombie))
        add(DspEffect(7, EffectMode.FAN, R.string.fan, R.drawable.effect_fan))
        add(DspEffect(8, EffectMode.KARAOKE, R.string.karaoke, R.drawable.effect_karaoke))
        add(
            DspEffect(
                9,
                EffectMode.BASSBOOSTER,
                R.string.bass_booster,
                R.drawable.effect_bass_booster
            )
        )
        add(DspEffect(10, EffectMode.ROBOT, R.string.robot, R.drawable.effect_robot))
        add(
            DspEffect(
                11,
                EffectMode.UNDERWATER,
                R.string.underwater,
                R.drawable.effect_under_water
            )
        )
        add(DspEffect(12, EffectMode.DARK, R.string.dark, R.drawable.effect_dark))
        add(DspEffect(13, EffectMode.CHIPMUNK, R.string.chipmunk, R.drawable.effect_chipmunk))
        add(DspEffect(14, EffectMode.LION, R.string.lion, R.drawable.effect_lion))
        add(DspEffect(15, EffectMode.OLDRADIO, R.string.old_radio, R.drawable.effect_old_radio))
    }


    data class DspEffect(
        val id: Int,
        val effectMode: EffectMode,
        val name: Int,
        val logo: Int
    )
}