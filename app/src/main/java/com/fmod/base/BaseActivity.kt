package com.fmod.base

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.fmod.units.PermissionUtil
import com.fmod.units.SystemSoundUtil
import org.greenrobot.eventbus.EventBus


abstract class BaseActivity<T : ViewBinding> : AppCompatActivity() {

    lateinit var viewBinding: T

    abstract fun initViewBinding(layoutInflater: LayoutInflater): T

    open fun registerEventBus() = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = initViewBinding(LayoutInflater.from(this))
        setContentView(viewBinding.root)
        if (registerEventBus()) {
            EventBus.getDefault().register(this)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (registerEventBus()) {
            EventBus.getDefault().unregister(this)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        PermissionUtil.onRequestPermissionsResult(this, requestCode, permissions, grantResults)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        PermissionUtil.onActivityResult(this, requestCode, resultCode, data)
        SystemSoundUtil.onActivityResult(this, requestCode, resultCode, data)
    }
}