package com.fmod.units

import android.app.Activity
import android.content.Intent
import android.provider.MediaStore

object SystemSoundUtil {

    private const val SELECT_IMAGE_REQUEST_CODE = 9999

    private var callback: ((String) -> Unit)? = null

    fun openSysTemSoundFile(context: Activity, callback: ((String) -> Unit)) {
        this.callback = callback
        val intent = Intent(Intent.ACTION_GET_CONTENT, MediaStore.Audio.Media.EXTERNAL_CONTENT_URI)
        intent.setDataAndType(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, "audio/*")
        context.startActivityForResult(intent, SELECT_IMAGE_REQUEST_CODE)
    }

    fun onActivityResult(
        context: Activity,
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        when (requestCode) {
            SELECT_IMAGE_REQUEST_CODE -> {
                if (resultCode == Activity.RESULT_OK) {
                    val path = PickUtils.getPath(context, data?.data)
                    callback?.invoke(path)
                }
            }
        }
    }
}