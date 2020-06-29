package com.hcanyz.zadapter.hodler

import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * @author hcanyz
 */
open class ZRecyclerViewHolder<DATA : Any>(internal val holder: ZViewHolder<DATA>, view: View) : RecyclerView.ViewHolder(view) {

    open fun update(data: DATA, payloads: List<Any>) {}

    open fun onViewRecycled() {}
}