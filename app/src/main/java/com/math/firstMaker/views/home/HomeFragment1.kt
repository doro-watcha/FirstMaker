package com.math.firstMaker.views.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import com.math.firstMaker.CommonConst
import com.math.firstMaker.broadcast.Broadcast
import com.math.firstMaker.common.disposedBy
import com.math.firstMaker.common.observeOnce
import com.math.firstMaker.databinding.FragmentHome1Binding
import com.math.firstMaker.debugE
import com.math.firstMaker.navigation.Navigator
import com.math.firstMaker.repository.AuthRepository
import com.math.firstMaker.views.home.adapter.BookAdapter
import com.math.firstMaker.views.home.adapter.ExamAdapter
import com.math.firstMaker.views.home.adapter.HomeworkAdapter
import com.math.firstMaker.views.home.adapter.PaperAdapter
import com.math.firstMaker.views.home.classList.ClassBindingAdapter
import io.reactivex.disposables.CompositeDisposable
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class HomeFragment1 : Fragment() {

    /**
     * Binding Instance
     */
    private var _mBinding: FragmentHome1Binding? = null
    private val mBinding: FragmentHome1Binding
        get() = _mBinding!!

    /**
     *  ViewModel Instance
     */

    private val mViewModel: HomeViewModel by sharedViewModel()

    private val compositeDisposable = CompositeDisposable()

    private val TAG = HomeFragment1::class.java.simpleName

    private val navigator : Navigator by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) =
        FragmentHome1Binding.inflate(inflater, container, false).also { _mBinding = it }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBinding.lifecycleOwner = viewLifecycleOwner
        mBinding.vm = mViewModel

        debugE(TAG, mViewModel)

        observeViewModel()
        initView()

        setupTypeRecyclerView()
        setupList()
        setupBroadcast()
        setupRefreshLayout()

    }

    private fun initView() {

    }


    private fun setupTypeRecyclerView() {


    }

    private fun setupList() {



        mBinding.mHomeworkRecyclerView.apply {

            adapter = HomeworkAdapter().apply {

                onClickEvent.subscribe{

                    mViewModel.apply {

                        curType.value = "숙제"
                        curTeacher.value= it.teacher
                        curTitle.value = it.title
                        curId = it.id
                        status.value = it.status
                        notes.value = it.notes
                        spendingTime.value = it.spendingTime

                        numChapters = it.numChapters
                        mainChapter = it.mainChapter


                        if (it.status == CommonConst.CollectionStatus.준비됨.name || it.status == CommonConst.CollectionStatus.진행중.name) {
                            currentPage.value = 1
                        } else if (it.status == CommonConst.CollectionStatus.완료됨.name) {

                            /**
                             * 바로 결과창으로 보내자
                             */
                            results.value = it.notes
                            correctRate.value = it.accurateRate.toString() + "% 정답률"
                            currentPage.value = 2
                        }

                    }

                }.disposedBy(compositeDisposable)
            }
        }

        mBinding.mExamRecyclerView.apply {

            adapter = ExamAdapter().apply{

                onClickEvent.subscribe{

                    mViewModel.apply {

                        curType.value = "시험"
                        curTitle.value = it.title
                        curId = it.id
                        status.value = it.status
                        notes.value = it.notes
                        spendingTime.value = it.spendingTime

                        numChapters = it.numChapters
                        mainChapter = it.mainChapter

                        if (it.status == CommonConst.CollectionStatus.준비됨.name || it.status == CommonConst.CollectionStatus.진행중.name) {
                            currentPage.value = 1
                        } else if (it.status == CommonConst.CollectionStatus.완료됨.name) {

                            /**
                             * 바로 결과창으로 보내자
                             */
                            results.value = it.notes
                            correctRate.value = it.accurateRate.toString() + "% 정답률"
                            currentPage.value = 2
                        }

                    }

                }.disposedBy(compositeDisposable)
            }
        }

        mBinding.mPaperRecyclerView.apply {

            adapter = PaperAdapter().apply {

                onClickEvent.subscribe {
                    mViewModel.apply {
                        curType.value = "문제지"
                        curTitle.value = it.title
                        curId = it.id
                        status.value = it.status
                        notes.value = it.notes
                        spendingTime.value = it.spendingTime

                        numChapters = it.numChapters
                        mainChapter = it.mainChapter


                        if (it.status == CommonConst.CollectionStatus.준비됨.name || it.status == CommonConst.CollectionStatus.진행중.name) {
                            currentPage.value = 1
                        } else if (it.status == CommonConst.CollectionStatus.완료됨.name) {

                            /**
                             * 바로 결과창으로 보내자
                             */
                            results.value = it.notes
                            correctRate.value = it.accurateRate.toString() + "% 정답률"
                            currentPage.value = 2

                        }
                    }
                }.disposedBy(compositeDisposable)
            }
        }


        mBinding.mRecyclerView.apply {

            adapter = ClassBindingAdapter().apply {
                onClickEvent.subscribe{
                    navigator.startClassDetailActivity(requireActivity(),it.id)
                }.disposedBy(compositeDisposable)
            }
        }

    }


    private fun observeViewModel() {

        mViewModel.apply {

            curUser.observe(viewLifecycleOwner) {
                var type = " 학생"
                if (it.type == "teacher") {
                    type = " 선생님"
                }

                mBinding.userName.text = it.name + type
            }

            refreshCompleted.observeOnce(viewLifecycleOwner){
                mBinding.mSwipeRefreshLayout.isRefreshing = false
            }
        }
    }

    private fun setupRefreshLayout() {

        mBinding.mSwipeRefreshLayout.setOnRefreshListener {
            if ( mViewModel.curUser.value?.type == "student") {
                mViewModel.refreshAll()
            } else {
                mBinding.mSwipeRefreshLayout.isRefreshing = false
            }
        }
    }

    private fun setupBroadcast() {

        Broadcast.apply {

            paperCreatedBroadcast.subscribe {
                mViewModel.refreshAll()

            }.disposedBy(compositeDisposable)

            classCreateBroadcast.subscribe{
                mViewModel.listClasses()
            }.disposedBy(compositeDisposable)
        }

    }


    companion object {
        fun newInstance() = HomeFragment1()
    }

}