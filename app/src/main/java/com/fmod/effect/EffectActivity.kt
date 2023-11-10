package com.fmod.effect

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.recyclerview.widget.GridLayoutManager
import com.fmod.R
import com.fmod.base.BaseActivity
import com.fmod.databinding.ActivityEffectBinding
import com.fmod.effect.adapter.EffectAdapter
import com.fmod.units.noDoubleClick
import com.fmodcore.FmodPlay

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
            }

            override fun onPause() {
                viewBinding.playIv.setImageResource(R.drawable.ic_play)
            }

            override fun onFinish() {
                viewBinding.playIv.setImageResource(R.drawable.ic_play)
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
    }

    override fun onDestroy() {
        super.onDestroy()
        fmodPlay.release()
    }
}