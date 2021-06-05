package com.math.firstMaker.views.makeCollection

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.observe
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.math.firstMaker.api.UnWrappingDataException
import com.math.firstMaker.broadcast.Broadcast
import com.math.firstMaker.common.disposedBy
import com.math.firstMaker.common.observeOnce
import com.math.firstMaker.databinding.FragmentMakeCollectionBinding
import com.math.firstMaker.debugE
import com.math.firstMaker.model.ShareRequest
import com.math.firstMaker.navigation.DialogNavigator
import com.math.firstMaker.navigation.Navigator
import com.math.firstMaker.views.makeCollection.dialog.SourcePickerDialog
import io.reactivex.disposables.CompositeDisposable
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class MakeCollectionFragment : Fragment() {


    private val TAG = MakeCollectionFragment::class.java.simpleName

    private val compositeDisposable = CompositeDisposable()

    /**
     * Binding Instance
     */
    private var _mBinding: FragmentMakeCollectionBinding? = null
    private val mBinding: FragmentMakeCollectionBinding
        get() = _mBinding!!

    /**
     *  ViewModel Instance
     */


    private val mViewModel: MakeCollectionViewModel by sharedViewModel()
    private val navigator: Navigator by inject()
    private val dialogNavigator : DialogNavigator by inject()



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) =
        FragmentMakeCollectionBinding.inflate(inflater, container, false)
            .also { _mBinding = it }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        mBinding.lifecycleOwner = viewLifecycleOwner
        mBinding.vm = mViewModel

        setupViewPager()
        observeViewModel()
        setupBroadcast()

    }

    private fun setupViewPager() {

        mBinding.mViewPager3.apply {

            isUserInputEnabled = false

            adapter = MakeCollectionTabAdapter(requireActivity().supportFragmentManager, 3)
        }

    }

    private fun setupBroadcast() {
        Broadcast.apply {

            makeCollectionReselectBroadcast.subscribe{
                mViewModel.currentPage.value = 0
            }.disposedBy(compositeDisposable)

            moveToFirstPageBroadcast.subscribe{

                if ( mViewModel.currentPage.value == 0 && it == 2) {
                    moveToHomeBroadcast.onNext(Unit)
                } else {
                    mViewModel.currentPage.value = 0
                }
            }.disposedBy(compositeDisposable)

            sourcePickBroadcast.subscribe{
                mViewModel.source.value = it.name
            }.disposedBy(compositeDisposable)
        }
    }

    private fun observeViewModel() {
        mViewModel.apply {
            currentPage.observe(viewLifecycleOwner) {
                if (it > 2) {
                    mBinding.mViewPager3.currentItem = 0
                    cleanViewModel()
                } else {
                    mBinding.mViewPager3.currentItem = it
                    mBinding.btnBack.visibility = View.VISIBLE
                }

                if (it == 0) {
                    mBinding.btnBack.visibility = View.GONE
                }


            }

            clickSourceDialog.observeOnce(viewLifecycleOwner){
                SourcePickerDialog.show(requireActivity().supportFragmentManager)
            }

            clickFirstStep.observeOnce(viewLifecycleOwner) {
                mBinding.mViewPager3.postDelayed(
                    Runnable { mBinding.mViewPager3.currentItem = 0 },
                    300
                )
            }

            onSaveCollection.observeOnce(viewLifecycleOwner) {
                Toast.makeText(context, "$it 생성을 완료하였습니다", Toast.LENGTH_SHORT).show()
                currentPage.value = 0
                clearData()
            }

            clickGiveCollection.observeOnce(viewLifecycleOwner){

                if ( problems.value != null && pickedSmallChapters.value != null && title.value != null ) {

                    val shareRequest = ShareRequest(
                        problems = problems.value!!,
                        numChapters = pickedSmallChapters.value?.size ?: 0,
                        mainChapter = pickedSmallChapters.value!![0].name,
                        title = title.value ?: "No Title"
                    )


                    navigator.startGiveCollectionActivity(requireActivity(), shareRequest, true)
                } else {
                    Toast.makeText(context, "빠진게 없는지 한 번 더 확인해주세요!", Toast.LENGTH_SHORT).show()
                }

            }
            errorInvoked.observeOnce(viewLifecycleOwner) {
                debugE(TAG, it.message)
                if (it is UnWrappingDataException) {
                    when (it.errorCode) {

                        102 -> Toast.makeText(context, "빠진게 없는지 한 번 더 확인해주세요!", Toast.LENGTH_SHORT).show()
                    }
                }

            }

        }
    }


    inner class MakeCollectionTabAdapter(fragmentManager: FragmentManager, pageCount: Int) :
        FragmentStateAdapter(fragmentManager, lifecycle) {
        private val _count: Int = pageCount


        override fun getItemCount(): Int {
            return _count
        }

        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> MakeCollectionFragment1.newInstance()
                1 -> MakeCollectionFragment2.newInstance()
                else -> MakeCollectionFragment3.newInstance()
            }
        }

    }


    companion object {
        fun newInstance() = MakeCollectionFragment()
    }
}

