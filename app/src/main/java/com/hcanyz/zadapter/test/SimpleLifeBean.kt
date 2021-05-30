package com.hcanyz.zadapter.test

import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hcanyz.zadapter.ZAdapter
import com.hcanyz.zadapter.hodler.ViewHolderHelper
import com.hcanyz.zadapter.hodler.ZLifecViewHolder
import com.hcanyz.zadapter.hodler.ZViewHolder
import com.hcanyz.zadapter.registry.IHolderCreater
import kotlin.random.Random

data class SimpleLifeBean(val iconId: Int, val title: String) : IHolderCreater {

    val list = arrayListOf<SimpleData>()


    override fun holderItemType(): Int {
        return HolderType.TYPE_SIMPLE_LIFE
    }

    override fun createHoldView(parent: ViewGroup, holderItemType: Int): ZViewHolder<out IHolderCreater> {
        return SimpleLifeViewHolder(parent)
    }
}


class SimpleLifeViewHolder(parent: ViewGroup, layoutId: Int = R.layout.holder_simple_life) : ZLifecViewHolder<SimpleLifeBean>(parent, layoutId) {
    private lateinit var tv_test: TextView
    private lateinit var iv_test: ImageView
    private lateinit var rvItemList: RecyclerView

    private val list = arrayListOf<SimpleData>()
    override fun initView(rootView: View) {
        super.initView(rootView)
        lifecycle.addObserver(LifecycleObserverTest())
        getViewModel(EventViewModel::class.java).clickEvent.observe(this, Observer {
            (it as? SimpleData)?.let {
                Log.i("tagg", "subKey" + it.key)
            }
        })
        tv_test = rootView.findViewById(R.id.tv_test)
        iv_test = rootView.findViewById(R.id.iv_test)
        rvItemList = rootView.findViewById(R.id.rvItemList)
        rvItemList.layoutManager = LinearLayoutManager(mContext, RecyclerView.HORIZONTAL, false)
        rvItemList.adapter = ZAdapter(list, ViewHolderHelper(this))


    }

    override fun initListener(rootView: View) {
        super.initListener(rootView)
        iv_test.setOnClickListener {
            val simpleData = SimpleData(R.mipmap.ic_launcher, "sigle" + Random(100).nextInt(100))
            mData.list.add(simpleData)
            list.add(simpleData)
            rvItemList.adapter?.notifyDataSetChanged()
        }
    }

    override fun update(data: SimpleLifeBean, payloads: List<Any>) {
        super.update(data, payloads)
        tv_test.text = data.title
        iv_test.setImageResource(data.iconId)
        list.clear()
        list.addAll(data.list)
        rvItemList.adapter?.notifyDataSetChanged()
    }
}


class LifecycleObserverTest : LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun holderCreated(owner: LifecycleOwner) {
        Log.e(TestZAdapterActivity.TAG, "HolderCreated -> can't use data")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun holderBindData(owner: LifecycleOwner) {
        Log.e(TestZAdapterActivity.TAG, "HolderBindData -> ${(owner as ZViewHolder<*>).mData}")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun holderAttachedToWindow(owner: LifecycleOwner) {
        Log.e(TestZAdapterActivity.TAG, "HolderAttachedToWindow -> ${(owner as ZViewHolder<*>).mData}")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun holderDetachedFromWindow(owner: LifecycleOwner) {
        Log.e(TestZAdapterActivity.TAG, "HolderDetachedFromWindow -> ${(owner as ZViewHolder<*>).mData}")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun holderDetachedFromWindowToo(owner: LifecycleOwner) {
        Log.e(TestZAdapterActivity.TAG, "HolderDetachedFromWindowToo -> ${(owner as ZViewHolder<*>).mData}")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun holderRecycled(owner: LifecycleOwner) {
        Log.e(TestZAdapterActivity.TAG, "HolderRecycled -> ${(owner as ZViewHolder<*>).mData}")
    }
}