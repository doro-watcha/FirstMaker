package com.math.firstMaker.views.home.classList.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.math.firstMaker.common.disposedBy
import com.math.firstMaker.databinding.FragmentStudentResultBinding
import com.math.firstMaker.navigation.Navigator
import com.math.firstMaker.views.home.adapter.ResultAdapter
import io.reactivex.disposables.CompositeDisposable
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

/**
 * created By DORO 1/3/21
 */



class StudentResultFragment : Fragment () {

    private lateinit var mBinding : FragmentStudentResultBinding
    private val mViewModel : ClassDetailViewModel by sharedViewModel()

    private val compositeDisposable = CompositeDisposable()

    private val navigator : Navigator by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = FragmentStudentResultBinding.inflate(inflater,container,false).also { mBinding = it}.root


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBinding.vm = mViewModel
        mBinding.lifecycleOwner = viewLifecycleOwner

        setupRecyclerView()
    }

    private fun setupRecyclerView() {

        mBinding.mRecyclerView.apply {

            adapter = ResultAdapter().apply {
                onClickItemEvent.subscribe{
                    navigator.startNoteListActivity(requireActivity(),mViewModel.curId,mViewModel.curType,it)
                }.disposedBy(compositeDisposable)
            }
        }
    }

    companion object {

        fun newInstance () = StudentResultFragment()
    }
}