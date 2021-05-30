package com.hcanyz.zadapter.hodler

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.lifecycle.*
import com.hcanyz.zadapter.registry.IHolderCreater

/**
 * @author hcanyz
 *
 * LifecycleOwner的使用方式参考androidx.lifecycle.LifecycleObserver
 * holder.getLifecycle().addObserver(new LifecycleObserver())
 * class LifecycleObserver { @OnLifecycleEvent(Lifecycle.Event.ON_CREATE) xxx{}}
 *
 * 需要依赖 kapt deps.android.lifecycleCompiler ，自动生成 xxx_LifecycleObserverTest_LifecycleAdapter
 *
 *
 * 解决内部事件注册
 *
 */
open class ZLifecViewHolder<DATA : IHolderCreater> internal constructor(context: Context, rootView: View) : ZViewHolder<DATA>(context, rootView), LifecycleOwner,
    ViewModelStoreOwner {

    constructor(context: Context, @LayoutRes layoutId: Int) : this(context, View.inflate(context, layoutId, null))

    constructor(parent: ViewGroup, @LayoutRes layoutId: Int) : this(
        parent.context,
        LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
    )

    private val viewModelProvide by lazy {
        ViewModelProvider(this)
    }

    fun <TYPE : ViewModel> getViewModel(modelClass: Class<TYPE>): TYPE {
        return viewModelProvide.get(modelClass)
    }

    private val mLifecycleRegistry: LifecycleRegistry by lazy {
        LifecycleRegistry(this)
    }

    private val modelStore by lazy {
        ViewModelStore()
    }

    override fun initView(rootView: View) {
        super.initView(rootView)
        getLifecycleRegistry().handleLifecycleEvent(Lifecycle.Event.ON_CREATE)
    }

    override fun update(data: DATA, payloads: List<Any>) {
        super.update(data, payloads)
        getLifecycleRegistry().handleLifecycleEvent(Lifecycle.Event.ON_START)
    }

    override fun onViewRecycled() {
        super.onViewRecycled()
        getLifecycleRegistry().handleLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    }

    override fun onViewAttachedToWindow() {
        super.onViewAttachedToWindow()
        getLifecycleRegistry().handleLifecycleEvent(Lifecycle.Event.ON_RESUME)
    }

    override fun onViewDetachedFromWindow() {
        getLifecycleRegistry().handleLifecycleEvent(Lifecycle.Event.ON_PAUSE)
        super.onViewDetachedFromWindow()
        getLifecycleRegistry().handleLifecycleEvent(Lifecycle.Event.ON_STOP)
    }

    override fun getLifecycle(): Lifecycle {
        return mLifecycleRegistry
    }

    internal fun getLifecycleRegistry(): LifecycleRegistry {
        return mLifecycleRegistry
    }

    override fun getViewModelStore(): ViewModelStore {
        return modelStore
    }
}

