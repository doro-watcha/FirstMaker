package com.math.firstMaker.views.workBook.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.math.firstMaker.databinding.FragmentAddWorkBook1Binding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

/**
 * created By DORO 3/14/21
 */

class AddWorkBookFragment1 : Fragment() {


    private lateinit var mBinding: FragmentAddWorkBook1Binding

    private val mViewModel : AddWorkBookViewModel by sharedViewModel()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = FragmentAddWorkBook1Binding.inflate(inflater,container,false).also { mBinding = it }.root


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBinding.vm = mViewModel
        mBinding.lifecycleOwner = viewLifecycleOwner

        observeViewModel()
    }

    private fun observeViewModel() {



    }

    companion object {

        fun newInstance() = AddWorkBookFragment1()
    }
}