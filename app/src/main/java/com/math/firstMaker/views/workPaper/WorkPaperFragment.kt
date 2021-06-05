package com.math.firstMaker.views.workPaper

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.math.firstMaker.common.observeOnce
import com.math.firstMaker.databinding.FragmentWorkPaperBinding
import com.math.firstMaker.navigation.Navigator
import com.math.firstMaker.debugE
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class WorkPaperFragment : Fragment() {

    private val TAG = "WorkPaperFragment"
    /**
     * Binding Instance
     */
    private var _mBinding: FragmentWorkPaperBinding? = null
    private val mBinding: FragmentWorkPaperBinding
        get() = _mBinding!!

    /**
     *  ViewModel Instance
     */

    private val mViewModel : WorkPaperViewModel by viewModel()

    private val navigator : Navigator by inject()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        FragmentWorkPaperBinding.inflate(inflater, container, false).also { _mBinding = it }.root



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBinding.lifecycleOwner = viewLifecycleOwner
        mBinding.vm = mViewModel

        setupRecyclerView()
        observeViewModel()
        setupSwipeRefreshLayout()
    }


    private fun setupRecyclerView() {

        mBinding.mCollectionRecyclerView.apply{
            adapter = CollectionBindingAdapter(mViewModel,viewLifecycleOwner).apply{
                takeEvent.subscribe{
                    mViewModel.createWorkPaperPublish(it)
                }
            }

        }

        mBinding.mPubishRecyclerView.apply{
            adapter = PublishBindingAdapter(mViewModel, viewLifecycleOwner).apply{
                resumeEvent.subscribe{

//                    navigator.startPublishPageActivity(
//                        activity = requireActivity())
                }
                retakeEvent.subscribe{
                    mViewModel.createWorkPaperPublish(it.workpaper!!)
                }
                resultEvent.subscribe{
                    navigator.startResultActivity(requireActivity(),it.id)
                }
                partialConfirmEvent.subscribe{
                    navigator.startResultActivity(requireActivity(),it.id)
                }
            }
        }
    }

    private fun observeViewModel(){
        mViewModel.apply{

            goPublish.observeOnce(viewLifecycleOwner){
       //         debugE(TAG, it.workpaper?.problems)
//                navigator.startPublishPageActivity(
//                    activity = requireActivity()
//                )
            }
            onLoadCompleted.observeOnce(viewLifecycleOwner){
                mBinding.mSwipeRefreshLayout.isRefreshing = false
            }
            onLoadFailed.observeOnce(viewLifecycleOwner){
                mBinding.mSwipeRefreshLayout.isRefreshing = false
                debugE(TAG,it.message)
            }
        }

    }

    private fun setupSwipeRefreshLayout() {
        mBinding.mSwipeRefreshLayout.setOnRefreshListener {
            // refresh일 경우 page는 초기값 1로 설정해서 findList를 해야함.
            // 만약 서버요청시 fail일 경우에는 기존 _curPage를 유지해서 페이지 넘겼을때 문제없이 구동되게 함.
            mViewModel.refreshAction()
        }

        /**
         * Programmatically trigger refresh
         */
        mBinding.mSwipeRefreshLayout.post {
            mViewModel.refreshAction()
        }
    }


    companion object {
        fun newInstance () = WorkPaperFragment()
    }

}