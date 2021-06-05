package com.math.firstMaker.common.widget.bottomDialog

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.math.firstMaker.R
import com.math.firstMaker.common.AutoClearedValue
import com.math.firstMaker.common.disposedBy
import com.math.firstMaker.databinding.BottomDialogStudentAddBinding
import com.math.firstMaker.views.home.classList.ClassBindingAdapter
import io.reactivex.disposables.CompositeDisposable
import org.koin.androidx.viewmodel.ext.android.viewModel


/**
 * created By DORO 2020/05/04
 */

class StudentAddBottomDialog : BottomSheetDialogFragment() {


    companion object {
        fun show(fm: FragmentManager) {
            val dialog = StudentAddBottomDialog()

            dialog.show(fm, dialog.tag)
        }
    }


    private val TAG = "StudentAddBottomDialog"
    override fun getTheme(): Int = R.style.BottomSheetDialog


    /**
     * Binding Instance
     */
    private var mBinding: BottomDialogStudentAddBinding by AutoClearedValue()

    private val mViewModel : StudentAddViewModel by viewModel()

    private val compositeDisposable = CompositeDisposable()


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val bottomSheetDialog = BottomSheetDialog(requireContext(), theme)

        bottomSheetDialog.behavior.peekHeight = 1800

        return bottomSheetDialog
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        return BottomDialogStudentAddBinding.inflate(inflater, container, false)
            .also { mBinding = it }.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        mBinding.lifecycleOwner = viewLifecycleOwner
        mBinding.vm = mViewModel
        setupRecyclerView()

        initView()
    }

    private fun initView() {

    }

    private fun setupRecyclerView() {

        mBinding.mRecyclerView.apply {

            adapter = ClassBindingAdapter().apply{

                onClickEvent.subscribe{

                }.disposedBy(compositeDisposable)
            }
        }
    }

}