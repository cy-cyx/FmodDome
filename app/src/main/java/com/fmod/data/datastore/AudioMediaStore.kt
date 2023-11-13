package com.fmod.data.datastore

import android.database.Cursor
import android.provider.MediaStore
import com.fmod.R
import com.fmod.units.CommonUtil
import com.fmod.units.FileUtil
import com.fmod.units.LogUtil
import java.io.File

class AudioMediaStore {

    private val TAG = "AudioMediaStore"

    fun execute(): ArrayList<FolderInfo> {

        val allFolder =
            FolderInfo(CommonUtil.appContext?.getString(R.string.all_folder) ?: "", "all-folder")
        val result = ArrayList<FolderInfo>()

        var c: Cursor? = null
        try {
            c = CommonUtil.appContext?.contentResolver?.query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null,
                MediaStore.Audio.Media.DEFAULT_SORT_ORDER
            )

            while (c?.moveToNext() == true) {
                val path = c.getString(c.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA)) // 路径

                val fileName = FileUtil.getName(path)
                val folder = FileUtil.getFolder(path)

                if (!File(path).exists()) {
                    continue
                }

                val name =
                    c.getString(c.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME)) // 歌曲名
                val album = c.getString(c.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM)) // 专辑
                val artist =
                    c.getString(c.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST)) // 作者
                val size = c.getLong(c.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE)) // 大小
                val duration =
                    c.getInt(c.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION)) // 时长
                val id = c.getInt(c.getColumnIndexOrThrow(MediaStore.Audio.Media._ID)) // 歌曲的id

                val info = FileInfo(type = FileType.AUDIO, path = path).apply {
                    this.path = path
                    this.name = name
                    this.album = album
                    this.artist = artist
                    this.size = size
                    this.duration = duration
                    this.id = id
                }

                // into all
                if (allFolder.cover == null) {
                    allFolder.cover = info
                }
                allFolder.files.add(info)

                // into folder
                var folderInfo = result.find { it.path == folder }
                if (folderInfo == null) {
                    folderInfo = FolderInfo(FileUtil.getName(folder), folder, cover = info)
                    result.add(folderInfo)
                }
                folderInfo.files.add(info)
            }

            // all into folder
            result.add(0, allFolder)

        } catch (e: Exception) {
            LogUtil.e(TAG, e.toString())
        } finally {
            try {
                c?.close()
            } catch (e: Exception) {
                // nodo
            }
        }
        return result
    }
}