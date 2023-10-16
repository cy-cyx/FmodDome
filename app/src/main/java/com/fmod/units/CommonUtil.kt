package com.fmod.units

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.TypedValue

object CommonUtil {

    var appContext: Context? = null

    fun dp2px(dpVal: Float): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, dpVal,
            appContext!!.resources.displayMetrics
        ).toInt()
    }

    fun getScreenWidth(): Int {
        return appContext?.resources?.displayMetrics?.widthPixels ?: 0
    }

    fun getScreenHeight(): Int {
        return appContext?.resources?.displayMetrics?.heightPixels ?: 0
    }

    fun getDrawable(res: Int): Drawable? {
        return appContext!!.resources?.getDrawable(res)
    }

    fun getColor(res: Int): Int {
        return appContext!!.resources.getColor(res)
    }

    fun getString(res: Int): String {
        return appContext!!.getString(res)
    }
}