package com.fmod.main

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import com.fmod.base.BaseActivity
import com.fmod.databinding.ActivityMainBinding
import com.fmod.units.PermissionUtil
import com.fmod.units.SystemSoundUtil
import com.fmod.units.noDoubleClick
import com.fmodcore.EffectMode
import com.fmodcore.FmodCore

class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun initViewBinding(layoutInflater: LayoutInflater): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    private var fmodCore = FmodCore()

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
        viewBinding.controlBn.noDoubleClick {
            fmodCore.initAndPlay(path, EffectMode.JINGSONG.mode)
        }

        viewBinding.luoliBn.noDoubleClick {

        }
        viewBinding.dashuBn.noDoubleClick {

        }
        viewBinding.jinsongBn.noDoubleClick {

        }
        viewBinding.gaoguaiBn.noDoubleClick {

        }
        viewBinding.konglingBn.noDoubleClick {

        }
    }

    private var path = ""

    fun openSoundFile() {
        SystemSoundUtil.openSysTemSoundFile(this@MainActivity) {
            path = it
            viewBinding.resTv.text = it
        }
    }
}