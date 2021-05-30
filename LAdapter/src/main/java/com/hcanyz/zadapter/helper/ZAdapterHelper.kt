package com.hcanyz.zadapter.helper

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hcanyz.zadapter.ZAdapter
import com.hcanyz.zadapter.registry.IHolderCreater
import com.hcanyz.zadapter.hodler.ZViewHolder

fun RecyclerView.bindZAdapter(zAdapter: ZAdapter<*>) {
    layoutManager = LinearLayoutManager(context)
    adapter = zAdapter
}

fun <DATA : IHolderCreater> ViewGroup.injectViewWithAdapter(adapter: ZAdapter<DATA>, startInjectPosition: Int = 0, iInjectHook: IInjectHook? = null) {
    // 取消之前的注册。防止多次刷新
    (tag as? RecyclerView.AdapterDataObserver)?.let {
        adapter.unregisterAdapterDataObserver(it)
    }
    onChanged(adapter, startInjectPosition, iInjectHook)
    //5. 观察 adapter
    val value = object : RecyclerView.AdapterDataObserver() {
        override fun onChanged() {
            super.onChanged()
            onChanged(adapter, startInjectPosition, iInjectHook)
        }
    }
    adapter.registerAdapterDataObserver(value)
    tag = value
}

private fun <DATA : IHolderCreater> ViewGroup.onChanged(adapter: ZAdapter<DATA>, startInjectPosition: Int, iInjectHook: IInjectHook?) {
    //初始化容器中每个view
    val itemCount = adapter.itemCount
    for (position in startInjectPosition until itemCount) {
        val childAt = getChildAt(position)
        //1. 当前位置view不存在，从adapter中获取，加入容器中，tag置为holder
        if (childAt == null) {
            createHolder7hook(adapter, position, iInjectHook)
        } else {
            //2. view存在，判断tag是否正常
            if (childAt.tag !is ZViewHolder<*>) {
                throw IllegalStateException("tag error")
            }
            if (((childAt.tag as? ZViewHolder<*>)?.mData as? IHolderCreater)?.holderItemType() != adapter.mDatas[position].holderItemType()) {
                createHolder7hook(adapter, position, iInjectHook)
            }
        }
    }
    //3. 删除多余view
    if (childCount > itemCount && childCount > startInjectPosition) {
        removeViewsInLayout(itemCount, childCount - itemCount)
    }
    //4. 给每个holder刷新数据
    for (position in startInjectPosition until childCount) {
        @Suppress("UNCHECKED_CAST")
        (getChildAt(position).tag as? ZViewHolder<DATA>)?.let {
            adapter.onBindViewHolder(it, position)
        }
    }
}

private fun <DATA : IHolderCreater> ViewGroup.createHolder7hook(adapter: ZAdapter<DATA>, position: Int, iInjectHook: IInjectHook?) {
    val holder = adapter.mDatas[position].createHoldView(this, adapter.mDatas[position].holderItemType())
    val view = holder.rootView()
    iInjectHook?.hookViewCreated(holder, view, position)
    addView(view, position)
    view.tag = holder
}

interface IInjectHook {
    fun hookViewCreated(holder: ZViewHolder<*>, view: View, position: Int) {
    }
}