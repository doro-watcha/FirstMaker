package com.math.firstMaker.views.workBook

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import com.math.firstMaker.common.disposedBy
import com.math.firstMaker.common.observeOnce
import com.math.firstMaker.databinding.FragmentWorkBook1Binding
import com.math.firstMaker.navigation.Navigator
import com.math.firstMaker.debugE
import io.reactivex.disposables.CompositeDisposable
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * created By DORO 2020/04/05
 */


class WorkBookFragment1 : Fragment() {

    private val TAG = "WorkBookFragment1"
    /**
     * Binding Instance
     */
    private var _mBinding: FragmentWorkBook1Binding? = null
    private val mBinding: FragmentWorkBook1Binding
        get() = _mBinding!!

    /**
     *  ViewModel Instance
     */

    private val mViewModel : WorkBookViewModel by sharedViewModel()

    private val navigator : Navigator by inject()

    private val compositeDisposable = CompositeDisposable()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        FragmentWorkBook1Binding.inflate(inflater, container, false).also { _mBinding = it }.root



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBinding.lifecycleOwner = viewLifecycleOwner
        mBinding.vm = mViewModel

        setupRecyclerView()
        observeViewModel()
    }


    private fun setupRecyclerView() {

        mBinding.mMyWorkBookRecyclerView.apply{
            adapter = WorkBookAdapter().apply{

                onClickEvent.subscribe{
                    mViewModel.getWorkBook(it.id, true)
                }.disposedBy(compositeDisposable)
            }

        }

        mBinding.mWorkBookRecyclerView.apply{
            adapter = WorkBookAdapter().apply{

                onClickEvent.subscribe{
                    mViewModel.getWorkBook(it.id)
                }.disposedBy(compositeDisposable)

            }
        }
    }

    private fun observeViewModel(){
        mViewModel.apply{


            errorInvoked.observeOnce(viewLifecycleOwner){
                debugE(TAG,it)
            }
        }

    }


    companion object {
        fun newInstance () = WorkBookFragment1()
    }

}