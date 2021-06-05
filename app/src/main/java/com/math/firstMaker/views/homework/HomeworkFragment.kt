package com.math.firstMaker.views.homework

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.math.firstMaker.R
import com.math.firstMaker.common.observeOnce
import com.math.firstMaker.databinding.FragmentHomeworkBinding
import com.math.firstMaker.navigation.Navigator
import com.math.firstMaker.debugE
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeworkFragment : Fragment() {

    private val TAG = "HomeworkFragment"
    /**
     * Binding Instance
     */
    private var _mBinding: FragmentHomeworkBinding? = null
    private val mBinding: FragmentHomeworkBinding
        get() = _mBinding!!

    /**
     *  ViewModel Instance
     */

    private val mViewModel: HomeworkViewModel by viewModel()

    private val navigator: Navigator by inject()

    val DIALOG_TEXT = "숙제를 시작하시겠습니까?"


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) =
        FragmentHomeworkBinding.inflate(inflater, container, false).also { _mBinding = it }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBinding.lifecycleOwner = viewLifecycleOwner
        mBinding.vm = mViewModel

        setupRecyclereView()
        setupSwipeRefreshLayout()
        observeViewModel()
    }

    fun setupRecyclereView() {

        mBinding.mRecyclerView.apply {


            adapter = HomeworkBindingAdapter(
                mViewModel,
                viewLifecycleOwner
            ).apply {
                takeEvent.subscribe {

                    val builder = AlertDialog.Builder(context)
                    val dialogView = layoutInflater.inflate(R.layout.basic_dialog, null)
                    //dialogView.basicDialogTitle.text = DIALOG_TEXT

                    builder.setView(dialogView)
                        .setPositiveButton("확인") { dialogInterface, i ->
//                            navigator.startPublishPageActivity(
//                                requireActivity())
                        }
                        .setNegativeButton("취소") { dialogInterface, i ->
                            /* 취소일 때 아무 액션이 없으므로 빈칸 */
                        }
                        .show()
                }
                resultEvent.subscribe {
                    navigator.startResultActivity(requireActivity(), it.id)
                }

                retakeEvent.subscribe {
                   // mViewModel.retakeHomework(it)

                }
                partialConfirmEvent.subscribe{
                    navigator.startResultActivity(requireActivity(), it.id)
                }

            }

        }
    }

    private fun setupSwipeRefreshLayout() {
//        mBinding.mSwipeRefreshLayout.setOnRefreshListener {
//            // refresh일 경우 page는 초기값 1로 설정해서 findList를 해야함.
//            // 만약 서버요청시 fail일 경우에는 기존 _curPage를 유지해서 페이지 넘겼을때 문제없이 구동되게 함.
//            mViewModel.refreshAction()
//        }
//
//        /**
//         * Programmatically trigger refresh
//         */
//        mBinding.mSwipeRefreshLayout.post {
//            mViewModel.refreshAction()
//        }
    }

    private fun observeViewModel (){


        mViewModel.apply{

            onLoadCompleted.observeOnce(viewLifecycleOwner){
                mBinding.mSwipeRefreshLayout.isRefreshing = false
            }

            onLoadFailed.observeOnce(viewLifecycleOwner){
                mBinding.mSwipeRefreshLayout.isRefreshing = false
                debugE(TAG, it.message)
            }
//            onRetake.observeOnce(viewLifecycleOwner){
////                navigator.startPublishPageActivity(
////                    requireActivity())
//            }
        }
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MyPageFragment.
         */
        // TODO: Rename and change types and number of parameters
        fun newInstance() = HomeworkFragment()
    }


}