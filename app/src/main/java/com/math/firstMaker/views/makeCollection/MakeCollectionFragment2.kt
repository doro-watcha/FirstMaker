package com.math.firstMaker.views.makeCollection

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import com.math.firstMaker.broadcast.Broadcast
import com.math.firstMaker.common.disposedBy
import com.math.firstMaker.databinding.FragmentMakeCollection2Binding
import com.math.firstMaker.debugE
import com.math.firstMaker.navigation.DialogNavigator
import com.math.firstMaker.views.makeCollection.dialog.adapter.StringAdapter
import com.math.firstMaker.views.makeCollection.dialog.adapter.BigChapterAdapter
import io.reactivex.disposables.CompositeDisposable
import it.sephiroth.android.library.rangeseekbar.RangeSeekBar
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


/**
 * created By DORO 2020-03-22
 */

class MakeCollectionFragment2 : Fragment() {


    private val TAG = MakeCollectionFragment2::class.java.simpleName

    private val compositeDisposable = CompositeDisposable()

    private val dialogNavigator : DialogNavigator by inject()
    /**
     * Binding Instance
     */
    private var _mBinding: FragmentMakeCollection2Binding? = null
    private val mBinding: FragmentMakeCollection2Binding
        get() = _mBinding!!

    /**
     *  ViewModel Instance
     */

    private val mViewModel : MakeCollectionViewModel by sharedViewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        FragmentMakeCollection2Binding.inflate(inflater, container, false).also { _mBinding = it }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        mBinding.lifecycleOwner = viewLifecycleOwner
        mBinding.vm = mViewModel

        initView()
        observeViewModel()
        setupRecyclerView()
        setupBroadcast()

    }

    private fun initView() {

        mBinding.apply {

            seekBar.setOnRangeSeekBarChangeListener( object : RangeSeekBar.OnRangeSeekBarChangeListener{
                override fun onProgressChanged(p0: RangeSeekBar?, p1: Int, p2: Int, p3: Boolean) {
                    mViewModel.minLevel.value = p1+1
                    mViewModel.maxLevel.value = p2+1

                    mBinding.txtMinLevel.text = (p1+1).toString() + "단계"
                    mBinding.txtMaxLevel.text = (p2+1)  .toString() + "단계"
                }

                override fun onStartTrackingTouch(p0: RangeSeekBar?) {

                }

                override fun onStopTrackingTouch(p0: RangeSeekBar?) {

                }


            })


        }
    }

    private fun observeViewModel () {


        mViewModel.apply{
            pickedSmallChapters.observe(viewLifecycleOwner){
                debugE(TAG, "WOW!")

            }


        }
    }

    private fun setupBroadcast () {

        Broadcast.apply {

            clickSmallChapterBroadcast.subscribe{
                debugE(TAG, it)
                mViewModel.pickedSmallChapters.value?.add(it)

                (mBinding.mSmallChapterRecyclerView.adapter as? StringAdapter)?.run {
                    items = mViewModel.pickedSmallChapters.value ?: listOf()
                    this.notifyDataSetChanged()
                }

                var totalNumbers = 0
                mViewModel.pickedSmallChapters.value?.forEach {smallChapter ->
                    totalNumbers += Integer.parseInt(smallChapter.numberOfProblems.get() ?: "")
                }

            }.disposedBy(compositeDisposable)

            deleteSmallChapterBroadcast.subscribe{
                mViewModel.pickedSmallChapters.value?.remove(it)

                (mBinding.mSmallChapterRecyclerView.adapter as? StringAdapter)?.run {
                    items = mViewModel.pickedSmallChapters.value ?: listOf()
                    this.notifyDataSetChanged()
                }

                var totalNumbers = 0
                mViewModel.pickedSmallChapters.value?.forEach {smallChapter ->
                    totalNumbers += Integer.parseInt(smallChapter.numberOfProblems.get() ?: "")
                }
            }.disposedBy(compositeDisposable)

        }
    }

    private fun setupRecyclerView () {
        mBinding.mRecyclerView.apply{
            adapter = BigChapterAdapter().apply {

                onClickEvent.subscribe{
                    dialogNavigator.showChapterPickerDialog(requireActivity().supportFragmentManager, it)
                }.disposedBy(compositeDisposable)
            }
        }

        mBinding.mSmallChapterRecyclerView.apply {

            adapter = StringAdapter()

        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        
        compositeDisposable.clear()
    }
    companion object {

        fun newInstance() = MakeCollectionFragment2()
    }


}
