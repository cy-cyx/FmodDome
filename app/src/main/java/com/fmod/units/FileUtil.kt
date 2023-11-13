package com.fmod.units

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.text.TextUtils
import androidx.core.content.FileProvider
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.util.Locale


object FileUtil {

    // 获得目录
    fun getFolder(file: String): String {
        val last = file.lastIndexOf("/")
        if (last == -1) return file
        return file.substring(0, last)
    }

    // 获得文件的名或者目录名
    fun getName(file: String): String {
        val last = file.lastIndexOf("/")
        if (last == -1) return file
        return file.substring(last + 1, file.length)
    }

    fun deleteImageById(context: Context, id: Int) {
        try {
            val resolver = context.contentResolver
            resolver.delete(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                MediaStore.Images.Media._ID + "= ?",
                arrayOf(id.toString())
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun deleteVideoById(context: Context, id: Int) {
        try {
            val resolver = context.contentResolver
            resolver.delete(
                MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                MediaStore.Video.Media._ID + "= ?",
                arrayOf(id.toString())
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun copyPrivateToVideo(context: Context, orgFilePath: String, displayName: String) {
        if (orgFilePath.isBlank()) return

        val values = ContentValues()
        values.put(MediaStore.Video.Media.DISPLAY_NAME, displayName)
        values.put(MediaStore.Video.Media.MIME_TYPE, "video/mp4") //MediaStore对应类型名
        values.put(MediaStore.Video.Media.TITLE, displayName)
        values.put(MediaStore.Video.Media.RELATIVE_PATH, "Movies/VoiceChanger")
        val external = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
        val resolver = context.contentResolver
        val insertUri = resolver.insert(external, values) //使用ContentResolver创建需要操作的文件
        var ist: InputStream? = null
        var ost: OutputStream? = null
        try {
            ist = FileInputStream(File(orgFilePath))
            if (insertUri != null) {
                ost = resolver.openOutputStream(insertUri)
            }
            if (ost != null) {
                val buffer = ByteArray(4096)
                var byteCount = 0
                while (ist.read(buffer).also { byteCount = it } != -1) {  // 循环从输入流读取 buffer字节
                    ost.write(buffer, 0, byteCount) // 将读取的输入流写入到输出流
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            try {
                ist?.close()
                ost?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    fun copyPrivateToAudio(context: Context, orgFilePath: String, displayName: String) {
        if (orgFilePath.isBlank()) return

        val values = ContentValues()
        values.put(MediaStore.Audio.Media.DISPLAY_NAME, displayName)
        values.put(MediaStore.Audio.Media.MIME_TYPE, "audio/wav") //MediaStore对应类型名
        values.put(MediaStore.Audio.Media.TITLE, displayName)
        values.put(MediaStore.Audio.Media.RELATIVE_PATH, "Music/VoiceChanger")
        val external = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        val resolver = context.contentResolver
        val insertUri = resolver.insert(external, values) //使用ContentResolver创建需要操作的文件
        var ist: InputStream? = null
        var ost: OutputStream? = null
        try {
            ist = FileInputStream(File(orgFilePath))
            if (insertUri != null) {
                ost = resolver.openOutputStream(insertUri)
            }
            if (ost != null) {
                val buffer = ByteArray(4096)
                var byteCount = 0
                while (ist.read(buffer).also { byteCount = it } != -1) {  // 循环从输入流读取 buffer字节
                    ost.write(buffer, 0, byteCount) // 将读取的输入流写入到输出流
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            try {
                ist?.close()
                ost?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    fun copyPrivateToImage(context: Context, orgFilePath: String, displayName: String) {
        if (orgFilePath.isBlank()) return

        val values = ContentValues()
        values.put(MediaStore.Images.Media.DISPLAY_NAME, displayName)
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg") //MediaStore对应类型名
        values.put(MediaStore.Images.Media.TITLE, displayName)
        values.put(MediaStore.Images.Media.RELATIVE_PATH, "DCIM/VoiceChanger")
        val external = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val resolver = context.contentResolver
        val insertUri = resolver.insert(external, values) //使用ContentResolver创建需要操作的文件
        var ist: InputStream? = null
        var ost: OutputStream? = null
        try {
            ist = FileInputStream(File(orgFilePath))
            if (insertUri != null) {
                ost = resolver.openOutputStream(insertUri)
            }
            if (ost != null) {
                val buffer = ByteArray(4096)
                var byteCount = 0
                while (ist.read(buffer).also { byteCount = it } != -1) {  // 循环从输入流读取 buffer字节
                    ost.write(buffer, 0, byteCount) // 将读取的输入流写入到输出流
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            try {
                ist?.close()
                ost?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    fun openVoice(context: Context, file: File) {
        try {
            val intent = Intent(Intent.ACTION_VIEW)
            //Android 7.0之后
            val uriForFile: Uri = FileProvider.getUriForFile(
                context,
                MyFileProvider.getFileProviderName(context),
                file
            )
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.setDataAndType(uriForFile,"audio/*")
            context.startActivity(intent)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }
}