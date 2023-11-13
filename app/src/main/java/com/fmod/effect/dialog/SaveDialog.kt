package com.fmod.effect.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import com.fmod.R
import com.fmod.databinding.DialogSaveBinding
import com.fmod.units.CommonUtil
import com.fmod.units.noDoubleClick

class SaveDialog(context: Context) : Dialog(context, R.style.Theme_translucentDialog) {

    private val viewBinding = DialogSaveBinding.inflate(LayoutInflater.from(context))

    var confirmListen: ((String) -> Unit)? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)

        val params = window?.attributes
        params?.width = CommonUtil.dp2px(300f)
        window?.attributes = params

        viewBinding.nameEt.setText("voice_${System.currentTimeMillis()}")

        viewBinding.cancelTv.noDoubleClick {
            dismiss()
        }
        viewBinding.saveTv.noDoubleClick {
            val name = viewBinding.nameEt.text.toString()
            if (name.isNotBlank()) {
                confirmListen?.invoke(name)
                dismiss()
            }
        }
    }
}