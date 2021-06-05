package com.math.firstMaker.views.workBook

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.math.firstMaker.common.disposedBy
import com.math.firstMaker.databinding.FragmentWorkBook3Binding
import io.reactivex.disposables.CompositeDisposable
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


/**
 * created By DORO 1/19/21
 */

class WorkBookFragment3 : Fragment() {

    private val TAG = WorkBookFragment3::class.java.simpleName

    private val compositeDisposable = CompositeDisposable()

    private val mViewModel : WorkBookViewModel by sharedViewModel()
    private lateinit var mBinding : FragmentWorkBook3Binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = FragmentWorkBook3Binding.inflate(inflater, container, false).also{mBinding =it}.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBinding.vm = mViewModel
        mBinding.lifecycleOwner = viewLifecycleOwner


        setupRecyclerView()
    }

    private fun setupRecyclerView(){

        mBinding.mRecyclerView.apply {

            adapter = WorkBookMiddleChapterAdapter().apply {

                clickEvent.subscribe{
                    //mViewModel.listNotes()
                }.disposedBy(compositeDisposable)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        compositeDisposable.clear()
    }

    companion object {

        fun newInstance () = WorkBookFragment3()
    }

}