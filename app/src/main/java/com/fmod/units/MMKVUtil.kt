package com.fmod.units

import android.content.Context
import com.tencent.mmkv.MMKV

object MMKVUtil {

    fun init(context: Context) {
        MMKV.initialize(context)
    }

    private val mmkv by lazy { MMKV.defaultMMKV(); }

    fun putBool(key: String, value: Boolean) {
        mmkv?.putBoolean(key, value)
    }

    fun getBool(key: String, default: Boolean = false): Boolean {
        return mmkv?.getBoolean(key, default) ?: default
    }

    fun putInt(key: String, value: Int) {
        mmkv?.putInt(key, value)
    }

    fun getInt(key: String, default: Int = 0): Int {
        return mmkv?.getInt(key, default) ?: default
    }

    fun putString(key: String, value: String) {
        mmkv?.putString(key, value)
    }

    fun getString(key: String): String {
        return mmkv?.getString(key, "") ?: ""
    }
}