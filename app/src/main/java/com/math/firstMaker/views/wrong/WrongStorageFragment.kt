package com.math.firstMaker.views.wrong

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.observe
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.math.firstMaker.R
import com.math.firstMaker.broadcast.Broadcast
import com.math.firstMaker.common.disposedBy
import com.math.firstMaker.common.observeOnce
import com.math.firstMaker.databinding.FragmentWrongStorageBinding
import com.math.firstMaker.api.UnWrappingDataException
import com.math.firstMaker.debugE
import com.math.firstMaker.dialog.showTextDialog
import com.math.firstMaker.navigation.Navigator
import io.reactivex.disposables.CompositeDisposable
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class WrongStorageFragment : Fragment() {

    private val TAG = WrongStorageFragment::class.java.simpleName

    private val navigator : Navigator by inject()

    private val compositeDisposable = CompositeDisposable()
    /**
     * Binding Instance
     */
    private var _mBinding: FragmentWrongStorageBinding? = null
    private val mBinding: FragmentWrongStorageBinding
        get() = _mBinding!!

    /**
     *  ViewModel
     */
    private val mViewModel: WrongStorageViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return FragmentWrongStorageBinding.inflate(inflater, container, false)
            .also { _mBinding = it }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBinding.vm = mViewModel
        mBinding.lifecycleOwner = viewLifecycleOwner

        observeViewModel()
        setupBroadcast()
        setupViewPager()

    }


    private fun setupViewPager() {

        mBinding.mViewPager2.apply {

            isUserInputEnabled = false

            adapter = WrongStorageAdapter(requireActivity().supportFragmentManager, 3)
        }

    }




    private fun observeViewModel() {

        mViewModel.apply {

            currentPage.observe(viewLifecycleOwner){
                mBinding.mViewPager2.currentItem = it
            }

            errorInvoked.observeOnce(viewLifecycleOwner){
                debugE(TAG,it.message)

                if (it is UnWrappingDataException) {
                    when (it.errorCode) {

                        // 선생님인데 오답노트 만들려고 함
                        432 -> showTextDialog(
                            titleResource = R.string.common_error,
                            bodyResource = R.string.txt_hoxy_teacher
                        )
                    }

                }
            }

            collectionCreated.observeOnce(viewLifecycleOwner){
                Toast.makeText(context,"${wrongType.value?.title}를 문제지로 생성하였습니다.",Toast.LENGTH_SHORT).show()
                currentPage.value = 0
            }

//            clickStartTest.observeOnce(viewLifecycleOwner){
//                navigator.startPublishPageActivity(requireActivity(), it.id)
//            }
        }

    }

    private fun setupBroadcast () {

        Broadcast.wrongReselectBroadcast.subscribe{
            mViewModel.currentPage.value = 0
        }.disposedBy(compositeDisposable)

        Broadcast.moveToFirstPageBroadcast.subscribe{

            if ( mViewModel.currentPage.value == 0 && it == 1) {
                Broadcast.moveToHomeBroadcast.onNext(Unit)
            } else {
                mViewModel.currentPage.value = 0
            }
        }.disposedBy(compositeDisposable)
    }

    inner class WrongStorageAdapter(fragmentManager: FragmentManager, pageCount: Int) :
        FragmentStateAdapter(fragmentManager, lifecycle) {
        private val _count: Int = pageCount

        override fun getItemCount(): Int {
            return _count
        }

        override fun createFragment(position: Int): Fragment {
            return when ( position ) {
                0 -> WrongStorageFragment1.newInstance()
                1 -> WrongStorageFragment2.newInstance()
                else -> WrongStorageFragment3.newInstance()
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()

        compositeDisposable.clear()
    }
    companion object {
        fun newInstance() = WrongStorageFragment()
    }


}