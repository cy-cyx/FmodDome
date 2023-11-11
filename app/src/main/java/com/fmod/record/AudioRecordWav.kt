package com.fmod.record

import android.annotation.SuppressLint
import android.media.AudioFormat
import android.media.AudioRecord
import android.media.MediaRecorder
import android.os.Environment
import android.util.Log
import com.fmod.units.CommonUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream

@SuppressLint("MissingPermission")
class AudioRecordWav {
    private val TAG = "AudioRecordPcm"

    private var audioRecord: AudioRecord? = null

    //音频输入-麦克风
    private val AUDIO_INPUT = MediaRecorder.AudioSource.MIC

    //采用频率
    //44100是目前的标准，但是某些设备仍然支持22050，16000，11025
    //采样频率一般共分为22.05KHz、44.1KHz、48KHz三个等级
    private val AUDIO_SAMPLE_RATE = 8000

    //声道 单声道
    private val AUDIO_CHANNEL = AudioFormat.CHANNEL_IN_MONO

    //编码
    private val AUDIO_ENCODING = AudioFormat.ENCODING_PCM_16BIT

    protected var wavFileName: String = ""
    protected var pcmFileName: String = ""

    // 缓冲区字节大小
    private var bufferSizeInBytes = 0

    init {
        bufferSizeInBytes =
            AudioRecord.getMinBufferSize(AUDIO_SAMPLE_RATE, AUDIO_CHANNEL, AUDIO_ENCODING)
        if (audioRecord == null) {
            audioRecord = AudioRecord(
                AUDIO_INPUT,
                AUDIO_SAMPLE_RATE,
                AUDIO_CHANNEL,
                AUDIO_ENCODING,
                bufferSizeInBytes
            )
        }
    }

    private var listener: RecordListener? = null

    fun setRecordListener(listener: RecordListener?) {
        this.listener = listener
    }

    fun startRecording(outFile: String) {
        wavFileName = outFile
        pcmFileName = getRecordFilePath()
        Log.i(TAG, "startRecording: ${audioRecord?.state}")
        if (audioRecord?.state == AudioRecord.STATE_INITIALIZED) {
            listener?.start()
            audioRecord?.startRecording()
            GlobalScope.launch(Dispatchers.IO) {
                writeDataTOFile()
            }
        } else {
            Log.i(TAG, "startRecording: state error")
        }
    }

    private fun getRecordFilePath(): String {
        val suffix = ".pcm"
        val file =
            File(CommonUtil.appContext!!.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)?.absolutePath + "/Record")
        if (!file.exists()) {
            file.mkdirs()
        }
        val path = File(file, "${System.currentTimeMillis()}$suffix")
        if (!path.exists()) {
            file.createNewFile()
        }
        return path.absolutePath
    }

    fun stopRecording() {
        GlobalScope.launch(Dispatchers.IO) {
            wavFileName = PcmToWav.makePcmToWav(pcmFileName, wavFileName, true)
            listener?.stop(wavFileName)
        }
        audioRecord?.stop()
        audioRecord?.release()
        audioRecord = null
    }

    fun getOutFilePath(): String = wavFileName

    private fun writeDataTOFile() {
        runCatching {
            // new一个byte数组用来存一些字节数据，大小为缓冲区大小
            val audiodata = ByteArray(bufferSizeInBytes)
            val fos = FileOutputStream(pcmFileName, false)
            audioRecord?.run {
                Log.i(TAG, "writeDataTOFile: $state")
                //将录音状态设置成正在录音状态
                while (state == AudioRecord.STATE_INITIALIZED) {
                    val readsize = read(audiodata, 0, bufferSizeInBytes)
                    if (AudioRecord.ERROR_INVALID_OPERATION != readsize) {
                        var sum = 0
                        for (i in 0 until readsize) {
                            sum += kotlin.math.abs(audiodata[i].toInt())
                        }
                        if (readsize > 0) {
                            val raw = sum / readsize
                            val lastVolumn = if (raw > 32) raw - 32 else 0
                            listener?.volume(lastVolumn)
                        }
                        if (readsize > 0 && readsize <= audiodata.size) {
                            fos.write(audiodata, 0, readsize)
                        }
                    }
                }
                fos.close()
            }
        }.onFailure {
            Log.e(TAG, "writeDataTOFile: ", it)
        }
    }

    interface RecordListener {
        fun start()

        fun volume(volume: Int)

        fun stop(outPath: String)
    }
}