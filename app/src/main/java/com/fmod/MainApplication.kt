package com.fmod

import android.app.Application
import android.content.Context
import com.fmod.units.CommonUtil
import com.fmod.units.MMKVUtil

class MainApplication : Application() {

    override fun attachBaseContext(base: Context?) {
        CommonUtil.appContext = this
        super.attachBaseContext(base)
    }

    override fun onCreate() {
        super.onCreate()
        MMKVUtil.init(this)
    }
}