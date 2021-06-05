package com.math.firstMaker.views.home


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.math.firstMaker.databinding.FragmentHome2Binding
import com.math.firstMaker.debugE
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

/**
 * created By DORO 2020/04/02
 */

class HomeFragment2 : Fragment() {

    /**
     * Binding Instance
     */
    private var _mBinding: FragmentHome2Binding? = null
    private val mBinding: FragmentHome2Binding
        get() = _mBinding!!

    /**
     *  ViewModel Instance
     */

    private val mViewModel: HomeViewModel by sharedViewModel()
    private val TAG = HomeFragment2::class.java.simpleName


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        FragmentHome2Binding.inflate(layoutInflater,container,false).also{_mBinding = it}.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBinding.vm = mViewModel
        mBinding.lifecycleOwner = viewLifecycleOwner

        debugE(TAG, "fragment2" )
    }


    companion object {
        fun newInstance () = HomeFragment2()
    }
}