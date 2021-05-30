package com.hcanyz.zadapter.hodler

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.recyclerview.widget.RecyclerView
import com.hcanyz.zadapter.ZAdapter
import com.hcanyz.zadapter.registry.IHolderCreater

/**
 * @author hcanyz
 *
 */
open class ZViewHolder<DATA : IHolderCreater> internal constructor(protected var mContext: Context, val mRootView: View) : RecyclerView.ViewHolder(mRootView) {

    constructor(context: Context, @LayoutRes layoutId: Int) : this(context, View.inflate(context, layoutId, null))

    constructor(parent: ViewGroup, @LayoutRes layoutId: Int) : this(
        parent.context,
        LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
    )

    lateinit var mData: DATA

    //用于传递事件源和其他消息
    var mViewHolderHelper: ViewHolderHelper? = null

    internal lateinit var zAdapter: ZAdapter<out IHolderCreater>

    //需要this对象构造函数走完才能进行init
    internal val initTask by lazy { init() }

    private fun init() {
        initView(mRootView)
        initListener(mRootView)
    }

    @CallSuper
    open fun initView(rootView: View) {
    }

    @CallSuper
    open fun initListener(rootView: View) {
    }

    @CallSuper
    open fun update(data: DATA, payloads: List<Any> = arrayListOf()) {
        this.mData = data
    }

    @CallSuper
    open fun onViewRecycled() {
    }

    @CallSuper
    open fun onViewAttachedToWindow() {
    }

    @CallSuper
    open fun onViewDetachedFromWindow() {
    }


    fun rootView(): View {
        initTask
        return mRootView
    }

    fun <T : View> findViewById(@IdRes id: Int): T {
        return rootView().findViewById(id)
    }

    fun addSelf2ViewGroup(viewGroup: ViewGroup): ZViewHolder<DATA> {
        viewGroup.addView(rootView())
        return this
    }

    fun performUpdate(data: DATA, payloads: List<Any> = arrayListOf()): ZViewHolder<DATA> {
        update(data, payloads)
        return this
    }
}