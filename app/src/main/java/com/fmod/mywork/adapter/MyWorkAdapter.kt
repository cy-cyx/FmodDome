package com.fmod.mywork.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.fmod.base.BaseViewHolder
import com.fmod.data.datastore.FileInfo
import com.fmod.databinding.ItemVoiceChangerBinding
import com.fmod.units.CommonUtil
import com.fmod.units.FileUtil
import com.fmod.units.noDoubleClick
import java.io.File

class MyWorkAdapter : RecyclerView.Adapter<ViewHolder>() {

    private val data = ArrayList<FileInfo>()

    fun setData(d: ArrayList<FileInfo>) {
        data.clear()
        data.addAll(d)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemVoiceChangerBinding.inflate(LayoutInflater.from(parent.context))
        binding.root.layoutParams = RecyclerView.LayoutParams(
            RecyclerView.LayoutParams.MATCH_PARENT,
            RecyclerView.LayoutParams.WRAP_CONTENT
        )
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        (holder as MyViewHolder).bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    class MyViewHolder(binding: ItemVoiceChangerBinding) :
        BaseViewHolder<ItemVoiceChangerBinding>(binding) {

        fun bind(info: FileInfo) {
            viewBinding.nameTv.text = info.name
            viewBinding.timeTv.text = "${info.duration / 1000}kb"
            viewBinding.root.noDoubleClick {
                FileUtil.openVoice(CommonUtil.appContext!!, File(info.path))
            }
        }
    }
}