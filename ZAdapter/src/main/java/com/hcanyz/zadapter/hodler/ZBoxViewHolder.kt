package com.hcanyz.zadapter.hodler

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes

/**
 * @author hcanyz
 */
open class ZBoxViewHolder : ZViewHolder<Any> {

    constructor(parent: ViewGroup, @LayoutRes layoutId: Int) : super(parent, layoutId)

    constructor(context: Context, rootView: View) : super(context, rootView)

    constructor(context: Context, @LayoutRes layoutId: Int) : super(context, layoutId)

    init {
        mData = Any()
    }
}