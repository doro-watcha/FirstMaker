package com.math.firstMaker.base.presenter

interface IBasePresenter <in TView: IBaseView> {
    fun attachView(view: TView)
    fun detachView()
}