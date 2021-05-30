package com.hcanyz.zadapter.test

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProviders
import com.hcanyz.zadapter.hodler.ViewHolderHelper
import com.hcanyz.zadapter.hodler.ZViewHolder
import com.hcanyz.zadapter.registry.IHolderCreater

interface IMulti : IHolderCreater {
    val iconId: Int
    var text: String
}

data class MultiData(override val iconId: Int, override var text: String, val isLayout2: Boolean) : IMulti {

    override fun holderItemType(): Int {
        if (isLayout2) {
            return HolderType.TYPE_MUTIL_2
        }
        return HolderType.TYPE_MUTIL
    }

    override fun createHoldView(parent: ViewGroup, holderItemType: Int): ZViewHolder<IMulti> {
        return when (holderItemType) {
            HolderType.TYPE_MUTIL_2 -> {
                MultiHolder(parent, R.layout.holder_multi_2)
            }
            else -> {
                MultiHolder(parent)
            }
        }
    }
}

data class MultiData2(val data2IconId: Int, val data2Text: String) : IMulti {

    override val iconId: Int = data2IconId

    override var text: String = data2Text

    override fun holderItemType(): Int {
        return HolderType.TYPE_MUTIL_2
    }

    override fun createHoldView(parent: ViewGroup, holderItemType: Int): ZViewHolder<IMulti> {
        return MultiHolder(parent, R.layout.holder_multi_2)
    }
}

class MultiHolder(parent: ViewGroup, layoutId: Int = R.layout.holder_multi_1) : ZViewHolder<IMulti>(parent, layoutId) {

    private lateinit var iv_test: ImageView
    private var tv_test: TextView? = null

    override fun initView(rootView: View) {
        super.initView(rootView)
        iv_test = rootView.findViewById(R.id.iv_test)
        tv_test = rootView.findViewById(R.id.tv_test)
    }

    override fun initListener(rootView: View) {
        super.initListener(rootView)
        iv_test.setOnClickListener {
            mViewHolderHelper?.getViewModel(EventViewModel::class.java)?.clickEvent?.postValue(mData)
        }
    }

    override fun update(data: IMulti, payloads: List<Any>) {
        super.update(data, payloads)
        iv_test.setImageResource(data.iconId)
        tv_test?.text = data.text
    }
}