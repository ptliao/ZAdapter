package com.hcanyz.zadapter.registry

import android.view.ViewGroup
import com.hcanyz.zadapter.hodler.ViewHolderHelper
import com.hcanyz.zadapter.hodler.ZViewHolder

/**
 * @author hcanyz
 */
interface IHolderCreater {

    fun holderItemType(): Int

    /**
     * 创建ViewHolder
     */
    fun createHoldView(parent: ViewGroup, holderItemType: Int): ZViewHolder<out IHolderCreater>
}