package com.hcanyz.zadapter.test


/**
 * 这里定义的holder Type值不能重复,后期考虑直接使用字节码插装来生成该对象
 * 一个Type对应一个Holder对象
 *
 */
class HolderType {
    companion object {
        const val TYPE_SIMPLE = 1
        const val TYPE_MUTIL = 2
        const val TYPE_MUTIL_2 = 3
        const val TYPE_SIMPLE_LIFE = 4

    }
}