package com.math.firstMaker.base.presenter

interface IBaseView

interface IBaseListView:IBaseView{
    fun onLoadCompleted()
    fun onLoadFailed(error:Throwable)
}