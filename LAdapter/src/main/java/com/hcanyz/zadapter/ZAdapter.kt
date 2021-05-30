package com.hcanyz.zadapter

import android.util.Log
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hcanyz.zadapter.hodler.ViewHolderHelper
import com.hcanyz.zadapter.hodler.ZViewHolder
import com.hcanyz.zadapter.registry.IHolderCreater


/**
 * @author hcanyz
 */
open class ZAdapter<DATA : IHolderCreater>(var mDatas: MutableList<DATA> = arrayListOf(), val mViewHolderHelper: ViewHolderHelper? = null) :
    RecyclerView.Adapter<ZViewHolder<DATA>>() {

    var showItemCount = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ZViewHolder<DATA> {
//      系统没有给position位置的回调，这里直接使用RecyclerView的第一个可见位置，向下查找所有匹配的ViewType值，创建对应的HolderView
        Log.i("tagg", "onCreateViewHolder , viewType :${viewType}")
        var firstPosition = (parent as? RecyclerView)?.let { recycler ->
            recycler.getChildAt(0)?.let {
                recycler.getChildAdapterPosition(it)
            }
        } ?: 0
        if (firstPosition == RecyclerView.NO_POSITION) {
            firstPosition = 0
        }
        var data: IHolderCreater? = null
//      在mDatas中获取最匹配的ViewType
        for (index in firstPosition until mDatas.size) {
            data = mDatas[index]
            Log.i("tagg", "find  position : ${index}, viewType :${data.holderItemType()}")
            if (viewType == data.holderItemType()) {
                Log.i("tagg", "find sucess  position : ${index}, viewType :${viewType}")
                break;
            }
        }
        if (data == null) {
            throw IllegalArgumentException("holder holderItemType not")
        } else {
            val holder = data.createHoldView(parent, viewType)
            holder.initTask
            holder.zAdapter = this
            holder.mViewHolderHelper = mViewHolderHelper  // 传递事件源处理
            return holder as ZViewHolder<DATA>
        }
    }

    override fun getItemViewType(position: Int): Int {
        return mDatas[position].holderItemType()
    }

    override fun getItemCount(): Int {
        if (showItemCount > -1) {
            return showItemCount
        }
        return mDatas.size
    }

    override fun onBindViewHolder(holder: ZViewHolder<DATA>, position: Int) {
        onBindViewHolderInner(holder, position)
    }

    override fun onBindViewHolder(holder: ZViewHolder<DATA>, position: Int, payloads: List<Any>) {
        onBindViewHolderInner(holder, position, payloads)
    }

    private fun onBindViewHolderInner(holder: ZViewHolder<DATA>, position: Int, payloads: List<Any> = emptyList()) {
        holder.update(mDatas[position], payloads)

    }

    override fun onViewRecycled(holder: ZViewHolder<DATA>) {
        super.onViewRecycled(holder)
        holder.onViewRecycled()
    }
}