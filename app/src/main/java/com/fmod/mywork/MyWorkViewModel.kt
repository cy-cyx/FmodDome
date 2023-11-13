package com.fmod.mywork

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.fmod.base.BaseViewModel
import com.fmod.data.datastore.AudioMediaStore
import com.fmod.data.datastore.FileInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MyWorkViewModel : BaseViewModel() {


    val voiceChangerLiveData = MutableLiveData<ArrayList<FileInfo>>()

    fun initData() {
        viewModelScope.launch(Dispatchers.IO) {
            AudioMediaStore().execute().find { it.path.contains("/Music/VoiceChanger") }?.let {
                voiceChangerLiveData.postValue(it.files.filter { it.path.contains(".wav") }
                    .toMutableList() as ArrayList<FileInfo>?)
            }
        }
    }
}