package com.fmod.units

import android.view.View

private var lastClickTimeStamp = -1L
private var sLimitDoubleInterval = 300

fun View?.noDoubleClick(block: ((View) -> Unit)) {
    this?.setOnClickListener {
        if (System.currentTimeMillis() - lastClickTimeStamp > sLimitDoubleInterval) {
            lastClickTimeStamp = System.currentTimeMillis()
            block.invoke(it)
        }
    }
}

fun View.gone() {
    this.visibility = View.GONE
}

fun View.visible() {
    this.visibility = View.VISIBLE
}