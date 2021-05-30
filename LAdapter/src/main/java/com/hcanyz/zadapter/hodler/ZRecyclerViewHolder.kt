//package com.hcanyz.zadapter.hodler
//
//import android.view.View
//import androidx.recyclerview.widget.RecyclerView
//import com.hcanyz.zadapter.registry.IHolderCreater
//
///**
// * @author hcanyz
// */
//open class ZRecyclerViewHolder<DATA: IHolderCreater>(internal val holder: ZViewHolder<DATA>, view: View) :
//    RecyclerView.ViewHolder(view) {
//
//    open fun update(data: DATA, payloads: List<Any>) {}
//
//    open fun onViewRecycled() {}
//}