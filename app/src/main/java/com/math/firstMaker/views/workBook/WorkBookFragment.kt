package com.math.firstMaker.views.workBook

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.observe
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.math.firstMaker.R
import com.math.firstMaker.broadcast.Broadcast
import com.math.firstMaker.common.disposedBy
import com.math.firstMaker.common.observeOnce
import com.math.firstMaker.databinding.FragmentWorkBookBinding
import com.math.firstMaker.navigation.Navigator
import com.math.firstMaker.debugE
import com.math.firstMaker.views.makeCollection.MakeCollectionFragment1
import com.math.firstMaker.views.makeCollection.MakeCollectionFragment2
import com.math.firstMaker.views.makeCollection.MakeCollectionFragment3
import io.reactivex.disposables.CompositeDisposable
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


/**
 * created By DORO 2020-02-28
 */

class WorkBookFragment : Fragment() {

    private val TAG = WorkBookFragment::class.java.simpleName

    private val compositeDisposable = CompositeDisposable()
    /**
     * Binding Instance
     */
    private var _mBinding: FragmentWorkBookBinding? = null
    private val mBinding: FragmentWorkBookBinding
        get() = _mBinding!!

    /**
     *  ViewModel Instance
     */

    private val mViewModel : WorkBookViewModel by sharedViewModel()

    private val navigator : Navigator by inject()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        FragmentWorkBookBinding.inflate(inflater, container, false).also { _mBinding = it }.root


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBinding.lifecycleOwner = viewLifecycleOwner
        mBinding.vm = mViewModel


        observeViewModel()
        setupViewPager()
        setupBroadcast()
    }

    private fun setupViewPager() {

        mBinding.mViewPager.apply {

            isUserInputEnabled = false
            adapter = WorkBookTabAdapter(requireActivity().supportFragmentManager,3)
        }
    }

    private fun observeViewModel () {

        mViewModel.apply {

            currentPage.observe(viewLifecycleOwner){
                debugE(TAG , "$it 으로 옮겼엉")
                mBinding.mViewPager.currentItem = it
            }

            onWorkBookLoadCompleted.observeOnce(viewLifecycleOwner){
                currentPage.value = 1
            }
            errorInvoked.observeOnce(viewLifecycleOwner){
                debugE(TAG, it)
            }

            buyChapterCompleted.observeOnce(viewLifecycleOwner){
                Toast.makeText(context, "구매 완료하였습니다",Toast.LENGTH_SHORT).show()
                listMyWorkBook()
            }

            clickAddWorkBook.observeOnce(viewLifecycleOwner){
                navigator.startAddWorkBookActivity(requireActivity())
            }

        }

    }

    private fun setupBroadcast() {

        Broadcast.moveToFirstPageBroadcast.subscribe({
            debugE(TAG, "in BroadcAst" + it)
            debugE(TAG, mViewModel.currentPage.value)

            if ( mViewModel.currentPage.value == 0 && it == 3) {
                Broadcast.moveToHomeBroadcast.onNext(Unit)
            } else {
                mViewModel.currentPage.value = 0
            }
        },{
            debugE(TAG, it)
        }).disposedBy(compositeDisposable)
    }

    inner class WorkBookTabAdapter(fragmentManager: FragmentManager, pageCount: Int) :
        FragmentStateAdapter(fragmentManager, lifecycle) {
        private val _count: Int = pageCount


        override fun getItemCount(): Int {
            return _count
        }

        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> WorkBookFragment1.newInstance()
                1 -> WorkBookFragment2.newInstance()
                else -> WorkBookFragment3.newInstance()
            }
        }

    }


    companion object {
        fun newInstance () = WorkBookFragment()
    }

}