package com.math.firstMaker.views.makeCollection

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import com.math.firstMaker.R
import com.math.firstMaker.broadcast.Broadcast
import com.math.firstMaker.common.addSchedulers
import com.math.firstMaker.common.disposedBy
import com.math.firstMaker.databinding.FragmentMakeCollection3Binding
import com.math.firstMaker.debugE
import com.math.firstMaker.navigation.Navigator
import com.math.firstMaker.views.home.classList.SearchStudentBindingAdapter
import com.math.firstMaker.views.home.classList.StudentBindingAdapter
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.BehaviorSubject
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import java.util.concurrent.TimeUnit


/**
 * created By DORO 2020-03-22
 */

class MakeCollectionFragment3 : Fragment() {


    companion object {
        private val TAG = MakeCollectionFragment3::class.java.simpleName

        fun newInstance() = MakeCollectionFragment3()
    }

    private val compositeDisposable = CompositeDisposable()

    /**
     * Binding Instance
     */
    private var _mBinding: FragmentMakeCollection3Binding? = null
    private val mBinding: FragmentMakeCollection3Binding
        get() = _mBinding!!

    /**
     *  ViewModel Instance
     */

    private val mViewModel : MakeCollectionViewModel by sharedViewModel()
    private val navigator : Navigator by inject()



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        FragmentMakeCollection3Binding.inflate(inflater, container, false).also { _mBinding = it }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        mBinding.lifecycleOwner = viewLifecycleOwner
        mBinding.vm = mViewModel


        observeViewModel()
        setupRecyclerView()
        setupBroadcast()

    }



    private fun observeViewModel() {

        mViewModel.apply {

            problems.observe(viewLifecycleOwner){
                mBinding.subTitle.text = resources.getString(R.string.make_collection_3_sub_title,it.size,it.size * 3)
                mBinding.problemNum.text = resources.getString(R.string.make_collection_3_problem_num, it.size)
            }

        }
    }

    private fun setupRecyclerView() {

        mBinding.mRecyclerView.apply {
            adapter = SearchResultAdapter(mViewModel, viewLifecycleOwner).apply {

                clickEvent.subscribe({
                    debugE(TAG, it)
                    navigator.startProblemSetActivity(requireActivity(), it, mViewModel.minLevel.value ?: 0, mViewModel.maxLevel.value ?: 0)
                }, {
                    debugE(TAG, it)
                }).disposedBy(compositeDisposable)
            }
        }
    }


    private fun setupBroadcast() {

        Broadcast.apply {

            //첫번째문제가 삭제되는거고 두번재 문제가 추가되는거
            problemChangeBroadcast.subscribe{


                // 문제 리스트 변경
                mViewModel.problems.value = mViewModel.problems.value?.filter { problem -> problem.id != it.first.id}
                mViewModel.problems.value = (mViewModel.problems.value ?: listOf() ) + listOf(it.second)


                // 문제 set 리스트 변경
                mViewModel.problemSetList.value?.forEach { problemSet ->

                    if ( problemSet.smallChapter.id == it.third.smallChapter.id) {
                        problemSet.problems = it.third.problems.minus(it.first)
                        problemSet.problems = problemSet.problems + listOf(it.second)
                    }
                }

            }.disposedBy(compositeDisposable)
        }


    }
}