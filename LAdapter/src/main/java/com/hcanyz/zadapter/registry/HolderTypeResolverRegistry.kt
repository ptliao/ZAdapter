//package com.hcanyz.zadapter.registry
//
//import android.view.ViewGroup
//import android.widget.TextView
//import com.hcanyz.zadapter.hodler.ViewHolderHelper
//import com.hcanyz.zadapter.hodler.ZViewHolder
//import java.util.*
//import java.util.concurrent.atomic.AtomicInteger
//
///**
// * @author hcanyz
// */
//class HolderTypeResolverRegistry {
//
//    private val maps: MutableMap<String, HolderCreatorInfo> = HashMap()
//
//    private val counter: AtomicInteger by lazy { AtomicInteger(1) }
//
//    fun registered(creatorName: String, creator: (parent: ViewGroup) -> ZViewHolder) {
//        val mutableMap = maps
//        mutableMap[creatorName]?.let {
//            throw IllegalStateException("Duplicate registration type：$creatorName $creator")
//        }
//        mutableMap[creatorName] = HolderCreatorInfo(counter.getAndIncrement(), creator)
//    }
//
//    internal fun findViewTypeByCreatorName(creatorName: String): Int {
//        return maps[creatorName]?.itemType ?: 0
//    }
//
//    internal fun <DATA : IHolderCreatorName> createHolderByHolderBean(viewType: Int, parent: ViewGroup, viewHolderHelper: ViewHolderHelper? = null): ZViewHolder {
//        //0说明没有找到
//        if (viewType != 0) {
//            maps.forEach { entry ->
//                if (entry.value.itemType == viewType) {
//                    @Suppress("UNCHECKED_CAST")
//                    val zViewHolder = entry.value.creator(parent) as ZViewHolder
//                    zViewHolder.mViewHolderHelper = viewHolderHelper
//                    return zViewHolder
//                }
//            }
//        }
//
//        val rootView = TextView(parent.context)
//        @Suppress("UsePropertyAccessSyntax")
//        rootView.setText("Not registration type：$viewType")
//        return ZViewHolder(parent.context, rootView)
//    }
//
//    private data class HolderCreatorInfo(val itemType: Int, val creator: (parent: ViewGroup) -> ZViewHolder)
//}