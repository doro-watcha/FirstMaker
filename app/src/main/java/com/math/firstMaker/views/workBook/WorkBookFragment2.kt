package com.math.firstMaker.views.workBook
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.math.firstMaker.common.disposedBy
import com.math.firstMaker.databinding.FragmentWorkBook2Binding
import io.reactivex.disposables.CompositeDisposable
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

/**
 * created By DORO 2020/04/05
 */


class WorkBookFragment2 : Fragment() {

    private val TAG = WorkBookFragment2::class.java.simpleName

    private val compositeDisposable = CompositeDisposable()

    private val mViewModel : WorkBookViewModel by sharedViewModel()
    private lateinit var mBinding : FragmentWorkBook2Binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = FragmentWorkBook2Binding.inflate(inflater, container, false).also{mBinding =it}.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBinding.vm = mViewModel
        mBinding.lifecycleOwner = viewLifecycleOwner


        setupRecyclerView()
    }

    private fun setupRecyclerView(){

        mBinding.mRecyclerView.apply {

            adapter = WorkBookBigChapterAdapter().apply {

                clickEvent.subscribe{
                    mViewModel.curBigChapter.value = it.bigChapter
                    mViewModel.listMiddleChapters(it.bigChapter)
                }.disposedBy(compositeDisposable)

                clickBuy.subscribe{
                    mViewModel.buyChapter(it)
                }.disposedBy(compositeDisposable)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        compositeDisposable.clear()
    }

    companion object {

        fun newInstance () = WorkBookFragment2()
    }

}