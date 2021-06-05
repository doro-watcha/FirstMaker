package com.math.firstMaker.base.adapter

interface IBaseAdapterContract {
    interface View {
        fun notifyAdapter()

    }

    interface Model<T> {
        fun addItems(items:ArrayList<T>)
        fun clearAll()
    }
}