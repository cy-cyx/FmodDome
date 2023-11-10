package com.fmodcore

enum class EffectMode(val mode: Int, val desc: String) {
    NORMAL(0, "normal"),      // 默认不加音效
    LUOLI(1, "loli"),         // 萝莉声音
    DASHU(2, "uncle"),        // 大叔声音
    JINGSONG(3, "uncle"),     // 惊悚声音
    GAOGUAI(4, "Funny"),      // 搞怪声音
    KONGLING(5, "Ethereal"),  // 空灵声音
    ZOMBIE(6, "Zombie"),      // 僵尸声音
    FAN(7, "Fan"),            // 风扇
    KARAOKE(8, "Karaoke"),    // 卡拉ok
    BASSBOOSTER(9, "Bass booster"),    // 低音增强器
    ROBOT(10, "Robot"),         // 机器人
    UNDERWATER(11, "UnderWater"),      // 水下
    DARK(12, "dark"),            // 黑暗
    CHIPMUNK(13, "Chipmunk"),    // 狐狸
    LION(14, "Lion"),            // 狮子
    OLDRADIO(15, "Old radio")    // 旧录音机
}