package com.fmod.units

import android.content.Context
import androidx.core.content.FileProvider

class MyFileProvider : FileProvider() {

    companion object {
        fun getFileProviderName(context: Context): String {
            return context.packageName + ".provider"
        }
    }
}