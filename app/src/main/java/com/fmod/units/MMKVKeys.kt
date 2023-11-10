package com.fmod.units

object MMKVKeys {
    const val keyFirstCheckPermission = "keyFirstCheckPermission"
}

fun String.getMMKVBool(default: Boolean = false) = MMKVUtil.getBool(this, default)
fun String.putMMKVBool(value: Boolean) = MMKVUtil.putBool(this, value)
fun String.getMMKVString() = MMKVUtil.getString(this)
fun String.putMMKVString(value: String) = MMKVUtil.putString(this, value)
fun String.putMMKVInt(value: Int) = MMKVUtil.putInt(this, value)
fun String.getMMKVInt() = MMKVUtil.getInt(this)
