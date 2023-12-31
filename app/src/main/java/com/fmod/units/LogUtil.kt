package com.fmod.units

import android.util.Log

object LogUtil {
    private val isDebug = true

    fun d(tag: String, msg: String) {
        if (!isDebug) return
        Log.d(tag, msg)
    }

    fun i(tag: String, msg: String) {
        if (!isDebug) return
        Log.i(tag, msg)
    }

    fun e(tag: String, msg: String) {
        if (!isDebug) return
        Log.e(tag, msg)
    }
}