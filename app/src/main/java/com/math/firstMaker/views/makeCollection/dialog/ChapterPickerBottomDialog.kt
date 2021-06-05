
package com.math.firstMaker.views.makeCollection.dialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import com.math.firstMaker.R
import com.math.firstMaker.common.AutoClearedValue
import com.math.firstMaker.databinding.BottomDialogChapterBinding
import com.math.firstMaker.model.BigChapter
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.math.firstMaker.broadcast.Broadcast
import com.math.firstMaker.common.observeOnce
import com.math.firstMaker.views.makeCollection.dialog.adapter.MiddleChapterAdapter
import io.reactivex.disposables.CompositeDisposable
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.core.parameter.parametersOf


/**
 * created By DORO 2020-03-22
 */
class ChapterPickerBottomDialog ( val bigChapter : BigChapter ): BottomSheetDialogFragment() {

    companion object {

        fun show(fm: FragmentManager, bigChapter: BigChapter ) {
            val dialog = ChapterPickerBottomDialog(bigChapter )

            dialog.show(fm, dialog.tag)
        }
    }


    private val TAG = ChapterPickerBottomDialog::class.java.simpleName
    override fun getTheme(): Int = R.style.BottomSheetDialog

    /**
     * Binding Instance
     */
    private var mBinding: BottomDialogChapterBinding by AutoClearedValue()

    private lateinit var mViewModel : ChapterViewModel


    private val compositeDisposable = CompositeDisposable()


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val bottomSheetDialog = BottomSheetDialog(requireContext(), theme)

        bottomSheetDialog.behavior.peekHeight = 1700


        return bottomSheetDialog
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        return BottomDialogChapterBinding.inflate(inflater, container, false)
            .also { mBinding = it }.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        mViewModel = getViewModel { parametersOf(bigChapter)}
        mBinding.lifecycleOwner = viewLifecycleOwner
        mBinding.vm = mViewModel

        setupRecyclerView()
        observeViewModel()

    }

    private fun setupRecyclerView() {

        mBinding.apply {
            mRecyclerView.apply {

                adapter = MiddleChapterAdapter(context).apply {

                }
            }
        }
    }

    private fun observeViewModel() {

        mViewModel.apply {

            clickConfirm.observeOnce(this@ChapterPickerBottomDialog){
                Broadcast.pickSmallChapterBroadcast.onNext(pickedSmallChapter)
                dismiss()
            }

        }
    }


}
