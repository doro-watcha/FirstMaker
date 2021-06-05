package com.math.firstMaker.base.view

import android.os.Bundle
import com.math.firstMaker.base.presenter.IBasePresenter
import com.math.firstMaker.base.presenter.IBaseView

/**
 * Presenter 생성
 * onCreate : attachView
 * onDestroy : detachView
 */
abstract class BasePresenterActivity<in TView : IBaseView, TPresenter : IBasePresenter<TView>> : BaseActivity(),
    IBaseView {

//    protected lateinit var presenter: TPresenter
//        private set

    abstract protected val presenter:TPresenter
    /**
     * 최종 Presenter 생성하는것은 상속받은 클래스에서 확정
     */
    //abstract fun onCreatePresenter(): TPresenter

    @Suppress("UNCHECKED_CAST")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //presenter = onCreatePresenter()
        presenter.attachView(this as TView)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }

}