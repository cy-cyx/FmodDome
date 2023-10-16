package com.fmodcore

class FmodCore {
    private val p: Long = -1
    private var init = false
    private var play = false

    fun initAndPlay(rec: String, mode: Int) {
        if (init) {
            release(p)
        }
        init = true
        playSound(rec, mode)
        play = true
    }

    fun pause() {
        pauseSound(p)
        play = false
    }

    fun replay() {
        replaySound(p)
    }

    fun release() {
        release(p)
    }

    /*native*/
    external fun playSound(rec: String, mode: Int): Long
    external fun pauseSound(p: Long)
    external fun replaySound(p: Long)
    external fun release(p: Long)

    companion object {
        init {
            System.loadLibrary("fmodcore")
        }
    }
}