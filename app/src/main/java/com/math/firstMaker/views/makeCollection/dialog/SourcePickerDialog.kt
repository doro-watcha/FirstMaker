package com.math.firstMaker.views.makeCollection.dialog

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.math.firstMaker.R
import com.math.firstMaker.broadcast.Broadcast
import com.math.firstMaker.common.AutoClearedValue
import com.math.firstMaker.common.disposedBy
import com.math.firstMaker.databinding.BottomDialogSourceBinding
import com.math.firstMaker.databinding.BottomDialogSubjectBinding
import com.math.firstMaker.views.makeCollection.dialog.adapter.SourcePickerAdapter
import com.math.firstMaker.views.makeCollection.dialog.adapter.SubjectAdapter
import io.reactivex.disposables.CompositeDisposable
import org.koin.androidx.viewmodel.ext.android.viewModel


/**
 * created By DORO 2/14/21
 */

class SourcePickerDialog : BottomSheetDialogFragment() {

    companion object {
        fun show(fm: FragmentManager) {
            val dialog = SourcePickerDialog()

            dialog.show(fm, dialog.tag)
        }
    }


    private val TAG = SourcePickerDialog::class.java.simpleName

    override fun getTheme(): Int = R.style.BottomSheetDialog

    /**
     * Binding Instance
     */
    private var mBinding: BottomDialogSourceBinding by AutoClearedValue()

    /**
     * viewModel
     */
    private val mViewModel: SourcePickerViewModel by viewModel()

    private val compositeDisposable = CompositeDisposable()


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val bottomSheetDialog = BottomSheetDialog(requireContext(), theme)

        bottomSheetDialog.behavior.peekHeight = 1500


        return bottomSheetDialog
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        return BottomDialogSourceBinding.inflate(inflater, container, false)
            .also { mBinding = it }.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        mBinding.lifecycleOwner = viewLifecycleOwner
        mBinding.vm = mViewModel

        initView()
        setupRecyclerView()
    }

    private fun initView() {




    }

    private fun setupRecyclerView() {

        mBinding.apply {
            mRecyclerView.apply {

                adapter = SourcePickerAdapter().apply {

                    onPickEvent.subscribe {
                        Broadcast.sourcePickBroadcast.onNext(it)
                        dismiss()
                    }.disposedBy(compositeDisposable)

                }


            }
        }
    }


}