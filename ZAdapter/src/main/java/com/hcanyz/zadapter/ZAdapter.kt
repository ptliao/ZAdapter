package com.hcanyz.zadapter

import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.RecyclerView
import com.hcanyz.zadapter.hodler.ViewHolderHelper
import com.hcanyz.zadapter.hodler.ZRecyclerViewHolder
import com.hcanyz.zadapter.registry.HolderTypeResolverRegistry
import com.hcanyz.zadapter.registry.IHolderCreatorName
import java.util.*


/**
 * @author hcanyz
 */
open class ZAdapter<DATA : IHolderCreatorName>(var mDatas: MutableList<DATA> = arrayListOf(),
                                               val mViewHolderHelper: ViewHolderHelper? = null) :
        RecyclerView.Adapter<ZRecyclerViewHolder<DATA>>() {

    val registry by lazy { HolderTypeResolverRegistry() }

    val adapterUUID: String by lazy { UUID.randomUUID().toString() }

    var showItemCount = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ZRecyclerViewHolder<DATA> {
        val holder = registry.createHolderByHolderBean<DATA>(viewType, parent, mViewHolderHelper)
        holder.initTask
        holder.zAdapter = this
        holder.getLifecycleRegistry().handleLifecycleEvent(Lifecycle.Event.ON_CREATE)
        return holder.recyclerViewHolder
    }

    override fun getItemViewType(position: Int): Int {
        return registry.findViewTypeByCreatorName(mDatas[position].holderCreatorName())
    }

    override fun getItemCount(): Int {
        if (showItemCount > -1) {
            return showItemCount
        }
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