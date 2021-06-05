package com.math.firstMaker.views.wrong

import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.observe
import com.math.firstMaker.R
import com.math.firstMaker.broadcast.Broadcast
import com.math.firstMaker.common.disposedBy
import com.math.firstMaker.common.observeOnce
import com.math.firstMaker.databinding.FragmentWrongStorage1Binding
import com.math.firstMaker.navigation.DialogNavigator
import com.math.firstMaker.DateParserUtil
import com.math.firstMaker.debugE
import io.reactivex.disposables.CompositeDisposable
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*


/**
 * created By DORO 2020/04/03
 */

class WrongStorageFragment1 : Fragment() {


    /**
     * Binding Instance
     */
    private var _mBinding: FragmentWrongStorage1Binding? = null
    private val mBinding: FragmentWrongStorage1Binding
        get() = _mBinding!!

    /**
     *  ViewModel
     */
    private val mViewModel: WrongStorageViewModel by sharedViewModel()

    private val DialogNavigator: DialogNavigator by inject()

    private val dateParserUtil: DateParserUtil by inject()

    private val TAG = "WrongStorageFragment1"

    private val dateDisposable = CompositeDisposable()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentWrongStorage1Binding.inflate(inflater, container, false).also { _mBinding = it }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBinding.lifecycleOwner = viewLifecycleOwner
        mBinding.vm = mViewModel


        observeViewModel()
        observeBroadcast()

        setupRecyclerView()

        debugE(TAG, mViewModel)

    }

    private fun setupRecyclerView () {


        mBinding.mRecyclerView.apply{

            adapter = WrongTypeAdapter(mViewModel).apply{
                onClickEvent.subscribe{
                    mViewModel.apply {
                        bigChapters.value = null
                        wrongType.value = it
                        listNotes(it)
                    }
                }

            }
        }
    }

    private fun observeViewModel() {


        mViewModel.apply {
            clickDatePicker.observeOnce(viewLifecycleOwner) {
                DialogNavigator.showDatePickerDialog(
                    requireActivity().supportFragmentManager,
                    start.value ?: Calendar.getInstance(),
                    end.value ?: Calendar.getInstance()
                )
            }

            start.observe(viewLifecycleOwner) {
                mBinding.btnDatePick.text = resources.getString(
                    R.string.wrong_storage_date,
                    dateParserUtil.parseToYMD(start.value?: Calendar.getInstance()),
                    dateParserUtil.parseToYMD(end.value?: Calendar.getInstance()))
            }
            end.observe(viewLifecycleOwner) {
                mBinding.btnDatePick.text = resources.getString(
                    R.string.wrong_storage_date,
                    dateParserUtil.parseToYMD(start.value?: Calendar.getInstance()),
                    dateParserUtil.parseToYMD(end.value?: Calendar.getInstance()))
            }
            clickBeforeStep.observeOnce(viewLifecycleOwner){
                currentPage.value = 0
            }

            onLoadCompleted.observeOnce(viewLifecycleOwner){
                currentPage.value = 1
            }
        }

    }

    private fun observeBroadcast() {

        Broadcast.datePickBroadcast.subscribe{

            debugE( TAG, it.first?.time)
            debugE( TAG, it.second?.time)

            mViewModel.start.value = it.first ?: Calendar.getInstance()
            mViewModel.end.value = it.second ?: Calendar.getInstance()

        }.disposedBy(dateDisposable)
    }

    override fun onDestroyView() {
        super.onDestroyView()

        dateDisposable.clear()
    }


    companion object {
        fun newInstance() = WrongStorageFragment1()
    }
}