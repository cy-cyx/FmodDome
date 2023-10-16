package com.fmod.base

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.viewbinding.ViewBinding

abstract class BaseViewHolder<T : ViewBinding>(binding: ViewBinding) : ViewHolder(binding.root) {

    lateinit var viewBinding: T

    init {
        viewBinding = binding as T
    }
}