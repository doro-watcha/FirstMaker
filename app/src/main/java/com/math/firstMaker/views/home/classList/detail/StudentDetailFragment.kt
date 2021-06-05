package com.math.firstMaker.views.home.classList.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.math.firstMaker.databinding.FragmentStudentDetailBinding
import com.math.firstMaker.views.home.adapter.ExamAdapter
import com.math.firstMaker.views.home.adapter.HomeworkAdapter
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


/**
 * created By DORO 1/3/21
 */

class StudentDetailFragment : Fragment() {

    private lateinit var mBinding : FragmentStudentDetailBinding
    private val mViewModel : ClassDetailViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = FragmentStudentDetailBinding.inflate(inflater,container,false).also { mBinding = it}.root


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBinding.vm = mViewModel
        mBinding.lifecycleOwner = viewLifecycleOwner

        setupRecyclerView()
    }

    private fun setupRecyclerView() {

        mBinding.apply {

            mExamRecyclerView.apply {

                adapter = ExamAdapter().apply {

                    onClickEvent.subscribe{

                        mViewModel.apply {
                            curTitle.value = it.title
                            status.value = it.status
                            results.value = it.notes
                            correctRate.value = it.accurateRate.toString() + "% 정답률"
                            spendingTime.value = it.spendingTime
                            curId = it.id
                            curType = "시험"

                            navigatePage.value = 2
                        }
                    }

                }
            }

            mHomeworkRecyclerView.apply {

                adapter = HomeworkAdapter().apply {

                    onClickEvent.subscribe{

                        mViewModel.apply {
                            curTitle.value = it.title
                            status.value = it.status
                            results.value = it.notes
                            correctRate.value = it.accurateRate.toString() + "% 정답률"
                            spendingTime.value = it.spendingTime
                            curId = it.id
                            curType = "숙제"

                            navigatePage.value = 2
                        }
                    }
                }
            }


        }
    }

    companion object {

        fun newInstance () = StudentDetailFragment()
    }
}