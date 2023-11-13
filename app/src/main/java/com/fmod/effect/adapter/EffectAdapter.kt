package com.fmod.effect.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.fmod.base.BaseViewHolder
import com.fmod.data.DspEffectRepository
import com.fmod.databinding.ItemEffectBinding
import com.fmod.units.gone
import com.fmod.units.noDoubleClick
import com.fmod.units.visible

class EffectAdapter : RecyclerView.Adapter<ViewHolder>() {

    private val data = DspEffectRepository.dsp
    private var id = 0

    var clickListen: ((DspEffectRepository.DspEffect) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemEffectBinding.inflate(LayoutInflater.from(parent.context))
        return MyHolderView(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        (holder as MyHolderView).bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class MyHolderView(binding: ItemEffectBinding) :
        BaseViewHolder<ItemEffectBinding>(binding) {

        @SuppressLint("NotifyDataSetChanged")
        fun bind(effect: DspEffectRepository.DspEffect) {
            viewBinding.logoIv.setImageResource(effect.logo)
            viewBinding.nameTv.setText(effect.name)

            if (effect.id == id) {
                viewBinding.tickIv.visible()
            } else {
                viewBinding.tickIv.gone()
            }

            viewBinding.root.noDoubleClick {
                clickListen?.invoke(effect)
                id = effect.id
                notifyDataSetChanged()
            }
        }
    }
}