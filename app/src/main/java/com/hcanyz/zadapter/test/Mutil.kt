package com.hcanyz.zadapter.test

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProviders
import com.hcanyz.zadapter.hodler.ZViewHolder
import com.hcanyz.zadapter.registry.IHolderCreatorName

interface IMulti : IHolderCreatorName {
    val iconId: Int
    val text: String
}

data class MultiData(override val iconId: Int, override val text: String, val isLayout2: Boolean) : IMulti {
    override fun holderCreatorName(): String {
        if (isLayout2) {
            return "${MultiData::class.java.name}_${R.layout.holder_multi_2}"
        }
        return MultiData::class.java.name
    }
}

data class MultiData2(val data2IconId: Int, val data2Text: String) : IMulti {

    override val iconId: Int = data2IconId

    override val text: String = data2Text

    override fun holderCreatorName(): String {
        return MultiData::class.java.name
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
            mViewHolderHelper?.requireFragmentActivity()?.let {
                ViewModelProviders.of(it).get(EventViewModel::class.java).clickEvent.postValue(mData)
            }
        }
    }

    override fun update(data: IMulti, payloads: List<Any>) {
        super.update(data, payloads)
        iv_test.setImageResource(data.iconId)
        tv_test?.text = data.text
    }
}