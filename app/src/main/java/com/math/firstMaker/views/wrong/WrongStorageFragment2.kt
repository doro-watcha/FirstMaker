package com.math.firstMaker.views.wrong

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import com.math.firstMaker.common.observeOnce
import com.math.firstMaker.databinding.FragmentWrongStorage2Binding
import com.math.firstMaker.navigation.DialogNavigator
import com.math.firstMaker.views.wrong.adapter.WrongBigChapterAdapter
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


/**
 * created By DORO 2020/04/03
 */

class WrongStorageFragment2 : Fragment() {



    /**
     * Binding Instance
     */
    private var _mBinding: FragmentWrongStorage2Binding? = null
    private val mBinding: FragmentWrongStorage2Binding
        get() = _mBinding!!

    /**
     *  ViewModel
     */
    private val mViewModel: WrongStorageViewModel by sharedViewModel()

    private val dialogNavigator : DialogNavigator by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = FragmentWrongStorage2Binding.inflate(inflater, container, false).also { _mBinding = it }.root


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBinding.vm = mViewModel
        mBinding.lifecycleOwner = viewLifecycleOwner

        setupRecyclerView()
        observeViewModel()
    }

    private fun setupRecyclerView(){
        mBinding.mRecyclerView.apply{

            adapter = WrongBigChapterAdapter().apply{

            }

        }

    }

    private fun observeViewModel () {

        mViewModel.apply{

            start.observe(viewLifecycleOwner){

            }

            end.observe(viewLifecycleOwner){

            }
        }



    }
    companion object {
        fun newInstance () = WrongStorageFragment2()
    }
}