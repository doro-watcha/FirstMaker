package com.math.firstMaker.views.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.observe
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.math.firstMaker.broadcast.Broadcast
import com.math.firstMaker.common.disposedBy
import com.math.firstMaker.common.observeOnce
import com.math.firstMaker.databinding.FragmentHomeBinding
import com.math.firstMaker.debugE
import com.math.firstMaker.model.ShareRequest
import com.math.firstMaker.navigation.Navigator
import io.reactivex.disposables.CompositeDisposable
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class HomeFragment : Fragment() {

    private val compositeDisposable = CompositeDisposable()
    /**
     * Binding Instance
     */
    private var _mBinding: FragmentHomeBinding? = null
    private val mBinding: FragmentHomeBinding
        get() = _mBinding!!

    /**
     *  ViewModel Instance
     */

    private val mViewModel: HomeViewModel by sharedViewModel()
    private val navigator : Navigator by inject()

    private val TAG = HomeFragment::class.java.simpleName

    private val mFragment1 = HomeFragment1.newInstance()
    private val mFragment2 = HomeFragment2.newInstance()
    private val mFragment3 = HomeFragment3.newInstance()
    private var mActiveFragment : Fragment = mFragment1


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        FragmentHomeBinding.inflate(inflater, container, false).also { _mBinding = it }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBinding.lifecycleOwner = viewLifecycleOwner
        mBinding.vm = mViewModel

        setupViewPager()
        setupBroadcast()

        observeViewModel()
    }



    private fun setupViewPager() {

        mBinding.mViewPager1.apply {

            isUserInputEnabled = false

            adapter = HomeFragmentAdapter(requireActivity().supportFragmentManager, 3)
        }


        }




    private fun observeViewModel() {


        mViewModel.apply{

            currentPage.observe(viewLifecycleOwner){
                mBinding.mViewPager1.currentItem = it
            }

            errorInvoked.observeOnce(viewLifecycleOwner) {
                debugE(TAG, it)
            }

            clickTest.observeOnce(viewLifecycleOwner) {
                navigator.startPublishPageActivity(requireActivity(), curType.value ?: "", curId)
            }

            clickAddClass.observeOnce(viewLifecycleOwner){
                navigator.startAddClassActivity(requireActivity())
            }

            clickGiveCollection.observeOnce(viewLifecycleOwner) {

                val shareRequest = ShareRequest(
                    problems = notes.value?.map{it.problem!!}!!,
                    numChapters = numChapters,
                    mainChapter = mainChapter,
                    title = curTitle.value ?: "No Title"
                )

                navigator.startGiveCollectionActivity(requireActivity(), shareRequest)


            }
        }
    }

    private fun setupBroadcast() {

        Broadcast.scoringCompletedBroadcast.subscribe{
            mViewModel.results.value = it.notes
            mViewModel.correctRate.value = it.accurateRate.toString() + "% 정답률"
            mViewModel.status.value = it.status
            mViewModel.spendingTime.value = it.spendingTime

            mViewModel.refreshAll()

            mViewModel.currentPage.value = 2
        }.disposedBy(compositeDisposable)

        Broadcast.homeReselectBroadcast.subscribe{
            mViewModel.navigatePage(0)
        }.disposedBy(compositeDisposable)

        Broadcast.moveToFirstPageBroadcast.subscribe{

            if ( mViewModel.currentPage.value == 0 && it == 0) {
                Broadcast.exitApplication.onNext(Unit)
            } else {
                mViewModel.currentPage.value = 0
            }
        }.disposedBy(compositeDisposable)
    }

    inner class HomeFragmentAdapter(fragmentManager: FragmentManager, pageCount: Int) :
        FragmentStateAdapter(fragmentManager, lifecycle) {
        private val _count: Int = pageCount


        override fun getItemCount(): Int {
            return _count
        }

        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> HomeFragment1.newInstance()
                1 -> HomeFragment2.newInstance()
                else -> HomeFragment3.newInstance()
            }
        }

    }


    companion object {
        fun newInstance() = HomeFragment()
    }

}