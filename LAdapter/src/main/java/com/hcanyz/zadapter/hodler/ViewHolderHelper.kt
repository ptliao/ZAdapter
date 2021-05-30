package com.hcanyz.zadapter.hodler

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders

/**
 * @author hcanyz
 */
open class ViewHolderHelper(
    private val zLifecViewHole: ZLifecViewHolder<*>? = null,
    private val fragment: Fragment? = null,
    private val fragmentActivity: FragmentActivity? = fragment?.activity
) {
    init {
        if (zLifecViewHole == null && fragment == null && fragmentActivity == null) {
            throw IllegalStateException("The arguments to the VieWholderHelper cannot be all empty.")
        }
    }

    fun <TYPE : ViewModel> getViewModel(modelClass: Class<TYPE>): TYPE {
        var model = zLifecViewHole?.getViewModel(modelClass)
        if (model == null) {
            model = fragment?.getViewModel(modelClass)
        }
        if (model == null) {
            model = fragmentActivity?.getViewModel(modelClass)
        }

        if (model == null) {
            //  测试环境直接抛异常出去
            throw IllegalStateException("The arguments to the VieWholderHelper cannot be all empty.")
        }
        return model
    }
}

fun <TYPE : ViewModel> Fragment.getViewModel(modelClass: Class<TYPE>): TYPE {
    return ViewModelProviders.of(this).get(modelClass)
}

fun <TYPE : ViewModel> FragmentActivity.getViewModel(modelClass: Class<TYPE>): TYPE {
    return ViewModelProviders.of(this).get(modelClass)
}