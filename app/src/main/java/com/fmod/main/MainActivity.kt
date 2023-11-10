package com.fmod.main

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import com.fmod.base.BaseActivity
import com.fmod.databinding.ActivityMainBinding
import com.fmod.effect.EffectActivity
import com.fmod.units.PermissionUtil
import com.fmod.units.SystemSoundUtil
import com.fmod.units.noDoubleClick
import com.fmodcore.EffectMode
import com.fmodcore.FmodPlay

class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun initViewBinding(layoutInflater: LayoutInflater): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewBinding.selectBn.noDoubleClick {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                PermissionUtil.requestRuntimePermissions(
                    this,
                    arrayOf(
                        Manifest.permission.READ_MEDIA_VIDEO,
                        Manifest.permission.READ_MEDIA_IMAGES
                    ),
                    object : PermissionUtil.IPermissionCallback {
                        override fun nextStep() {
                            openSoundFile()
                        }
                    })
            } else {
                PermissionUtil.requestRuntimePermissions(
                    this,
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    object : PermissionUtil.IPermissionCallback {
                        override fun nextStep() {
                            openSoundFile()
                        }
                    })
            }
        }

        viewBinding.gotoAddDsp.noDoubleClick {
            EffectActivity.start(this, path)
        }
    }

    private var path = ""

    fun openSoundFile() {
        SystemSoundUtil.openSysTemSoundFile(this@MainActivity) {
            path = it
        }
    }
}