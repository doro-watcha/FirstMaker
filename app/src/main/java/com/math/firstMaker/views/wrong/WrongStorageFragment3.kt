package com.math.firstMaker.views.wrong

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.math.firstMaker.databinding.FragmentWrongStorage3Binding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

/**
 * created By DORO 2020/04/03
 */

class WrongStorageFragment3  : Fragment() {


    /**
     * Binding Instance
     */
    private var _mBinding: FragmentWrongStorage3Binding? = null
    private val mBinding: FragmentWrongStorage3Binding
        get() = _mBinding!!

    /**
     *  ViewModel
     */
    private val mViewModel: WrongStorageViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = FragmentWrongStorage3Binding.inflate(inflater, container, false).also { _mBinding = it }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBinding.vm = mViewModel
        mBinding.lifecycleOwner = viewLifecycleOwner
    }

    companion object {
        fun newInstance () = WrongStorageFragment3()
    }
}