package com.math.firstMaker.views.makeCollection

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import com.math.firstMaker.R
import com.math.firstMaker.broadcast.Broadcast
import com.math.firstMaker.common.disposedBy
import com.math.firstMaker.common.observeOnce
import com.math.firstMaker.databinding.FragmentMakeCollection1Binding
import com.math.firstMaker.navigation.DialogNavigator
import com.math.firstMaker.navigation.Navigator
import com.math.firstMaker.debugE
import io.reactivex.disposables.CompositeDisposable
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


/**
 * created By DORO 2020-03-22
 */

class MakeCollectionFragment1 : Fragment() {

    companion object {

        fun newInstance() = MakeCollectionFragment1()
    }


    /**
     * Binding Instance
     */
        private var _mBinding: FragmentMakeCollection1Binding? = null
    private val mBinding: FragmentMakeCollection1Binding
        get() = _mBinding!!

    /**
     *  ViewModel Instance
     */
    private val mViewModel : MakeCollectionViewModel by sharedViewModel()

    private val navigator : Navigator by inject()
    private val dialogNavigator : DialogNavigator by inject()
    private val compositeDisposable = CompositeDisposable()
    private val TAG = "MakeCollectionFragment1"


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        FragmentMakeCollection1Binding.inflate(inflater, container, false).also { _mBinding = it }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        mBinding.lifecycleOwner = viewLifecycleOwner
        mBinding.vm = mViewModel

        observeViewModel()
        observeBroadcast()
    }

    private fun observeViewModel () {


        mViewModel.apply {


            clickCourse.observeOnce(viewLifecycleOwner){
                dialogNavigator.showSubjectPickDialog(requireActivity().supportFragmentManager)
            }
        }
    }

    private fun observeBroadcast() {

        Broadcast.COURSE_PICK_BROADCAST.subscribe{
            mViewModel.subject.value = it
        }.disposedBy(compositeDisposable)

    }

    override fun onPause() {
        super.onPause()
    }

}