package com.fmod.mywork

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.fmod.base.BaseActivity
import com.fmod.databinding.ActivityMyWorkBinding
import com.fmod.mywork.adapter.MyWorkAdapter

class MyWorkActivity : BaseActivity<ActivityMyWorkBinding>() {

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, MyWorkActivity::class.java))
        }
    }

    override fun initViewBinding(layoutInflater: LayoutInflater): ActivityMyWorkBinding {
        return ActivityMyWorkBinding.inflate(layoutInflater)
    }

    private val viewModel by viewModels<MyWorkViewModel>()

    private val myWorkAdapter = MyWorkAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        viewBinding.dataRv.apply {
            layoutManager = LinearLayoutManager(this@MyWorkActivity)
            adapter = myWorkAdapter
        }

        viewModel.voiceChangerLiveData.observe(this, Observer {
            myWorkAdapter.setData(it)
        })
        viewModel.initData()

    }
}