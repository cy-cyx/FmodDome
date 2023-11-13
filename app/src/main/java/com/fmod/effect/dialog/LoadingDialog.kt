package com.fmod.effect.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import com.fmod.R
import com.fmod.databinding.DialogLoadingBinding
import com.fmod.units.CommonUtil

class LoadingDialog(context: Context) : Dialog(context, R.style.Theme_translucentDialog) {

    val viewBinding = DialogLoadingBinding.inflate(LayoutInflater.from(context))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)

        val params = window?.attributes
        params?.width = CommonUtil.dp2px(100f)
        params?.height = CommonUtil.dp2px(100f)
        window?.attributes = params

        setCanceledOnTouchOutside(false)
        setCancelable(false)
    }
}