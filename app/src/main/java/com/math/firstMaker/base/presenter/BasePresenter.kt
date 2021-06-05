package com.math.firstMaker.base.presenter

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

@Suppress("unused")
abstract class BasePresenter<T: IBaseView> : IBasePresenter<T> {
    private var _disposables: CompositeDisposable? = CompositeDisposable()
    protected var view:T? = null
        private set


    override fun attachView(view: T) {
        this.view = view
    }

    /**
     * Activity / Fragment onDestroy() 에서 호출됨
     */
    override fun detachView() {
        this.view = null

        _disposables?.clear()
        _disposables = null
    }

    protected fun addToDisposables(disposable: Disposable) {
        _disposables?.add(disposable)
    }
}