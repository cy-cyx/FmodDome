package com.fmod.effect

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.fmod.R
import com.fmod.base.BaseActivity
import com.fmod.databinding.ActivityEffectBinding
import com.fmod.effect.adapter.EffectAdapter
import com.fmod.effect.dialog.LoadingDialog
import com.fmod.effect.dialog.SaveDialog
import com.fmod.units.FileUtil
import com.fmod.units.noDoubleClick
import com.fmodcore.FmodPlay
import kotlinx.coroutines.launch
import java.io.File

class EffectActivity : BaseActivity<ActivityEffectBinding>() {

    companion object {

        val sCodeRes = "sCodeRes"

        fun start(context: Context, res: String) {
            context.startActivity(Intent(context, EffectActivity::class.java).apply {
                putExtra(sCodeRes, res)
            })
        }
    }

    private val fmodPlay = FmodPlay()

    override fun initViewBinding(layoutInflater: LayoutInflater): ActivityEffectBinding {
        return ActivityEffectBinding.inflate(layoutInflater)
    }

    private val effectAdapter = EffectAdapter()
    private val loadingDialog by lazy { LoadingDialog(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewBinding.effectRv.apply {
            layoutManager = GridLayoutManager(context, 4)
            adapter = effectAdapter
        }

        val path = intent.getStringExtra(sCodeRes)
        if (path == "") finish()

        fmodPlay.setSoundRes(path)
        fmodPlay.setListen(object : FmodPlay.FmodPlayListen {
            override fun onStart() {
                viewBinding.playIv.setImageResource(R.drawable.ic_pause)
                viewBinding.playAnimLv.playAnimation()
            }

            override fun onPause() {
                viewBinding.playIv.setImageResource(R.drawable.ic_play)
                viewBinding.playAnimLv.pauseAnimation()
            }

            override fun onFinish() {
                viewBinding.playIv.setImageResource(R.drawable.ic_play)
                viewBinding.playAnimLv.pauseAnimation()
            }

        })

        viewBinding.playIv.noDoubleClick {
            if (fmodPlay.isPlay) {
                fmodPlay.pause()
            } else {
                fmodPlay.play()
            }
        }
        effectAdapter.clickListen = {
            fmodPlay.setEffect(it.effectMode.mode)
        }

        viewBinding.saveTv.noDoubleClick {
            if (fmodPlay.isPlay) {
                fmodPlay.pause()
            }

            SaveDialog(this).apply {
                confirmListen = {
                    loadingDialog.show()
                    val filePath = "${cacheDir?.absolutePath}/${it}.wav"
                    File(filePath).createNewFile()
                    fmodPlay.save(filePath) {
                        FileUtil.copyPrivateToAudio(this@EffectActivity, filePath, "${it}.wav")
                        lifecycleScope.launch {
                            loadingDialog.dismiss()
                            Toast.makeText(
                                this@EffectActivity,
                                R.string.success,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }.show()


        }

        viewBinding.backIv.noDoubleClick {
            finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        fmodPlay.release()
    }
}