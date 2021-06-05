package com.math.firstMaker.views.home.classList.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import com.math.firstMaker.common.addSchedulers
import com.math.firstMaker.common.disposedBy
import com.math.firstMaker.databinding.FragmentStudentListBinding
import com.math.firstMaker.views.home.classList.SearchStudentBindingAdapter
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.BehaviorSubject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import java.util.concurrent.TimeUnit


/**
 * created By DORO 1/3/21
 */

class StudentListFragment : Fragment() {

    private val compositeDisposable = CompositeDisposable()

    private lateinit var mBinding : FragmentStudentListBinding
    private val mViewModel : ClassDetailViewModel by sharedViewModel()

    private val studentNameChanged : BehaviorSubject<String> = BehaviorSubject.create()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = FragmentStudentListBinding.inflate(inflater,container,false).also { mBinding = it}.root


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBinding.vm = mViewModel
        mBinding.lifecycleOwner = viewLifecycleOwner

        setupRecyclerView()
        listenStudentNameChanged()
        observeViewModel()

    }

    private fun setupRecyclerView() {

        mBinding.mRecyclerView.apply {

            adapter = MyStudentAdapter().apply {

                onRemoveEvent.subscribe{
                    mViewModel.deleteStudent(it.id)
                }.disposedBy(compositeDisposable)

                onClickEvent.subscribe{
                    mViewModel.pickStudent.value = it
                    mViewModel.listStudentCollection()
                }.disposedBy(compositeDisposable)

            }
        }

        mBinding.searchRecyclerView.apply {

            adapter = SearchStudentBindingAdapter().apply {


                onClickItem.subscribe{
                    mViewModel.curStudent.value = it
                    mViewModel.searchStudents.value = null
                    mViewModel.studentName.value = ""

                }.disposedBy(compositeDisposable)

            }
        }
    }

    private fun listenStudentNameChanged() {


        studentNameChanged
            .debounce(300L, TimeUnit.MILLISECONDS)
            .addSchedulers()
            .subscribe {
                mViewModel.listUsersByStudentName(it)
            }.disposedBy(compositeDisposable)
    }

    private fun observeViewModel() {

        mViewModel.apply {

            studentName.observe(viewLifecycleOwner){
                studentNameChanged.onNext(it)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        compositeDisposable.clear()
    }

    companion object {

        fun newInstance () = StudentListFragment()
    }
}