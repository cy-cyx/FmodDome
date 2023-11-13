package com.fmod.data.datastore

data class FolderInfo(
    var name: String,
    var path: String,
    var cover: FileInfo? = null,
    var files: ArrayList<FileInfo> = arrayListOf()
)

data class FileInfo(
    val type: FileType,    // 类别

    var id: Int = -1,           // id
    var name: String = "",      // 名字
    var path: String,           // 路径
    var album: String = "",     // 专辑
    var artist: String = "",    // 作者
    var size: Long = -1L,        // 大小
    var duration: Int = -1,     // 时长
    var data: Long = -1L         // 日期
)

enum class FileType() {
    UNKNOWN,   // 未知
    IMAGE,     // 图片
    VIDEO,     // 视频
    AUDIO,     // 音频
    ARCHIVES,  // 文档
    DOWNLOAD   // 下架
}