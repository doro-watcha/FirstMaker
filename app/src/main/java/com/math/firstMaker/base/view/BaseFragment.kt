package com.math.firstMaker.base.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseFragment : Fragment() {
    private var _disposables: CompositeDisposable? = CompositeDisposable()
    protected lateinit var _fragmentNavigation:FragmentNavigation
    internal var _instance = 0

    @LayoutRes
    abstract fun getLayoutResId():Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val args :Bundle? = arguments
        if (args != null) {
            _instance = args.getInt(ARGS_INSTANCE)
        }
    }

    @CallSuper
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(getLayoutResId(), container, false)

    }

    override fun onDestroy() {
        _disposables?.clear()
        _disposables = null

        super.onDestroy()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is FragmentNavigation) {
            _fragmentNavigation = context
        }
    }
    protected fun addToDisposables(disposable: Disposable) {
        _disposables?.add(disposable)
    }

    fun refresh() {

    }

    interface FragmentNavigation {
        fun pushFragment(fragment: Fragment, sharedElementList: List<Pair<View, String>>?=null)
    }

    companion object {
        const val ARGS_INSTANCE = "frag_instance"
    }
}