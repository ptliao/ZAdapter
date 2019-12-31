package com.hcanyz.zadapter

import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.RecyclerView
import com.hcanyz.zadapter.hodler.ViewHolderHelper
import com.hcanyz.zadapter.hodler.ZRecyclerViewHolder
import com.hcanyz.zadapter.registry.IHolderCreatorName
import com.hcanyz.zadapter.registry.HolderTypeResolverRegistry
import java.util.*


/**
 * @author hcanyz
 */
open class ZAdapter<DATA : IHolderCreatorName>(var mDatas: MutableList<DATA> = arrayListOf(),
                                               val mViewHolderHelper: ViewHolderHelper? = null) :
        RecyclerView.Adapter<ZRecyclerViewHolder<DATA>>() {

    val holderCreatorRegistry by lazy { HolderTypeResolverRegistry() }

    /**
     * adapter 标签
     */
    val adapterId: String by lazy { UUID.randomUUID().toString() }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ZRecyclerViewHolder<DATA> {
        val holder = holderCreatorRegistry.createHolderByHolderBean<DATA>(viewType, parent, mViewHolderHelper)
        holder.getLifecycleRegistry().handleLifecycleEvent(Lifecycle.Event.ON_CREATE)

        holder.initTask

        return holder.recyclerViewHolder
    }

    override fun getItemViewType(position: Int): Int {
        return holderCreatorRegistry.findViewTypeByCreatorName(mDatas[position].holderCreatorName())
    }

    override fun getItemCount(): Int {
        return mDatas.size
    }

    override fun onBindViewHolder(holder: ZRecyclerViewHolder<DATA>, position: Int) {
        onBindViewHolderInner(holder, position)
    }

    override fun onBindViewHolder(holder: ZRecyclerViewHolder<DATA>, position: Int, payloads: List<Any>) {
        onBindViewHolderInner(holder, position, payloads)
    }

    private fun onBindViewHolderInner(holder: ZRecyclerViewHolder<DATA>, position: Int, payloads: List<Any> = emptyList()) {
        holder.update(mDatas[position], payloads)

        holder.holder.getLifecycleRegistry().handleLifecycleEvent(Lifecycle.Event.ON_START)
    }

    override fun onViewRecycled(holder: ZRecyclerViewHolder<DATA>) {
        super.onViewRecycled(holder)
        holder.onViewRecycled()
        holder.holder.getLifecycleRegistry().handleLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    }
}