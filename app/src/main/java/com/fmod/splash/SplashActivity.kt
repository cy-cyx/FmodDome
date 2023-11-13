package com.fmod.splash

import android.os.Bundle
import android.view.LayoutInflater
import com.fmod.base.BaseActivity
import com.fmod.databinding.ActivitySplashBinding
import com.fmod.main.MainActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : BaseActivity<ActivitySplashBinding>() {

    override fun initViewBinding(layoutInflater: LayoutInflater): ActivitySplashBinding {
        return ActivitySplashBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        GlobalScope.launch(Dispatchers.Main) {
            delay(5000)
            MainActivity.start(this@SplashActivity)
            finish()
        }
        viewBinding.progressTv.startProgress(5000)
    }
}