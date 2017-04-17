package com.entregoya.entregouser.access

import android.content.Context

abstract class BaseAccess<T : Any> {
    /**
     * While without reactive properties
     */
    protected lateinit var content: T

    /**
     * Should call once in  Application instance to cache in memory target value
     */
    abstract fun init(context: Context)

    /**
     * Common getter
     */
    open fun getValue(): T = content
    /**
     * To save an content from modification by property syntaxis
     */
    fun update(newContent: T) {
        content = newContent
    }
}