package com.fmod.main

import android.Manifest
import android.content.Intent
import android.media.MediaRecorder
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import com.fmod.R
import com.fmod.base.BaseActivity
import com.fmod.databinding.ActivityMainBinding
import com.fmod.effect.EffectActivity
import com.fmod.record.AudioRecordWav
import com.fmod.units.PermissionUtil
import com.fmod.units.SystemSoundUtil
import com.fmod.units.noDoubleClick
import com.fmodcore.EffectMode
import com.fmodcore.FmodPlay
import java.io.IOException

class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun initViewBinding(layoutInflater: LayoutInflater): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    private var isStartRecord = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            PermissionUtil.requestRuntimePermissions(
                this,
                arrayOf(
                    Manifest.permission.READ_MEDIA_VIDEO,
                    Manifest.permission.READ_MEDIA_IMAGES
                ),
                object : PermissionUtil.IPermissionCallback {
                    override fun nextStep() {
                    }
                })
        } else {
            PermissionUtil.requestRuntimePermissions(
                this,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                object : PermissionUtil.IPermissionCallback {
                    override fun nextStep() {
                    }

                })
        }

        viewBinding.recordIv.noDoubleClick {

//            SystemSoundUtil.openSysTemSoundFile(this, {
//                EffectActivity.start(this, it)
//            })
            if (!isStartRecord) {
                startRecord()
            } else {
                stopRecord()
            }
        }
    }

    private var fileName = ""

    private var recorder: AudioRecordWav? = null

    private fun startRecord() {
        PermissionUtil.requestRuntimePermissions(
            this,
            arrayOf(
                Manifest.permission.RECORD_AUDIO
            ),
            object : PermissionUtil.IPermissionCallback {
                override fun nextStep() {
                    fileName = "${cacheDir?.absolutePath}/record_${System.currentTimeMillis()}.wav"
                    recorder = AudioRecordWav().apply {
                        setRecordListener(object : AudioRecordWav.RecordListener {
                            override fun start() {

                            }

                            override fun volume(volume: Int) {

                            }

                            override fun stop(outPath: String) {
                                EffectActivity.start(this@MainActivity, outPath)
                            }

                        })
                    }
                    recorder?.startRecording(fileName)

                    isStartRecord = true
                    viewBinding.recordIv.setImageResource(R.drawable.ic_stop_record)
                }
            })

    }

    private fun stopRecord() {
        recorder?.stopRecording()

        isStartRecord = false
        viewBinding.recordIv.setImageResource(R.drawable.ic_record)
    }
}
