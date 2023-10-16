package com.fmodcore

import org.fmod.FMOD

class FmodCore {
    private var p: Long = -1
    private var play = false

    fun initAndPlay(rec: String, mode: Int) {
        if (p != -1L) {
            release(p)
        }
        p = playSound(rec, mode)
        play = true
    }

    fun pause() {
        if (p == -1L) return
        pauseSound(p)
        play = false
    }

    fun replay() {
        if (p == -1L) return
        replaySound(p)
    }

    fun release() {
        if (p == -1L) return
        release(p)
        p = -1L
    }

    /*native*/
    external fun playSound(rec: String, mode: Int): Long
    external fun pauseSound(p: Long)
    external fun replaySound(p: Long)
    external fun release(p: Long)

    companion object {
        init {
            System.loadLibrary("fmodL");
            System.loadLibrary("fmod");
            System.loadLibrary("fmodcore")
        }
    }
}