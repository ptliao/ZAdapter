package com.hcanyz.zadapter.registry

/**
 * @author hcanyz
 */
interface IHolderCreatorName {

    /**
     * holder创建器的唯一id，默认是当前bean的class name
     * 如果一个bean对应多个创建器，需要复写这个方法，返回不同的创建器id
     */
    fun holderCreatorName(): String {
        return this::class.java.name
    }
}