package com.math.firstMaker.base.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.math.firstMaker.base.presenter.IBasePresenter
import com.math.firstMaker.base.presenter.IBaseView

abstract class BasePresenterFragment<in TView : IBaseView, TPresenter : IBasePresenter<TView>> : BaseFragment(),
    IBaseView {

    abstract protected val presenter:TPresenter


    @Suppress("UNCHECKED_CAST")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        presenter.attachView(this as TView)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }
}