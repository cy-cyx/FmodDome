package com.fmod.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import org.greenrobot.eventbus.EventBus


abstract class BaseFragment<T : ViewBinding> : Fragment() {

    lateinit var viewBinding: T

    abstract fun initViewBinding(inflater: LayoutInflater): T

    open fun useEventBus(): Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (useEventBus()) EventBus.getDefault().register(this)
        viewBinding = initViewBinding(inflater)
        return viewBinding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        if (useEventBus()) EventBus.getDefault().unregister(this)
    }
}