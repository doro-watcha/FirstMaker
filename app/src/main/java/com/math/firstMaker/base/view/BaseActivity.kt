package com.math.firstMaker.base.view

import android.content.Context
import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseActivity : AppCompatActivity() {
    protected var _disposables: CompositeDisposable? = CompositeDisposable()

    @LayoutRes
    abstract fun getLayoutResId(): Int

    fun getContext(): Context = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutResId())

    }

    override fun onDestroy() {
        _disposables?.clear()
        _disposables = null
        super.onDestroy()
    }
    protected fun addToDisposables(disposable: Disposable) {
        _disposables?.add(disposable)
    }

}