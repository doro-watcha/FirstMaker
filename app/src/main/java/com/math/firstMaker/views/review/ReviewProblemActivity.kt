package com.math.firstMaker.views.review

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import com.math.firstMaker.R
import com.math.firstMaker.args.ProblemListArgs
import com.math.firstMaker.common.observeOnce
import com.math.firstMaker.databinding.ActivityReviewBinding
import com.math.firstMaker.model.Problem
import com.math.firstMaker.navigation.Navigator
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.core.parameter.parametersOf

class ReviewProblemActivity : AppCompatActivity() {

    companion object {
        private const val ARG_PROBLEM_LIST = "ARG_PROBLEM_LIST"
        private const val ARG_EDIT_MODE = "ARG_EDIT_MODE"


        fun newIntent(context: Context, problems: ProblemListArgs, editMode : Boolean): Intent {
            return Intent(context, ReviewProblemActivity::class.java).apply {
                putExtra(ARG_PROBLEM_LIST, problems)
                putExtra(ARG_EDIT_MODE, editMode)
            }
        }
    }


    /**
     * Binding Instance
     */
    private lateinit var mBinding: ActivityReviewBinding

    /**
     * ViewModel Instance
     */
    private lateinit var mViewModel: ReviewViewModel

    private val TAG = "ReviewProblemActivity"


    private val args by lazy { intent?.getParcelableExtra(ARG_PROBLEM_LIST) as? ProblemListArgs }
    private val editMode by lazy { intent?.getBooleanExtra(ARG_EDIT_MODE, false) }


    override fun onCreate(savedInstanceState: Bundle?) {

        mBinding = ActivityReviewBinding.inflate(LayoutInflater.from(this))
        mBinding.lifecycleOwner = this

        mViewModel = getViewModel { parametersOf(args) }
        mBinding.vm = mViewModel

        if ( editMode == false ) {
            mBinding.btnConfirm.visibility = View.GONE
        }
        setContentView(mBinding.root)

        setupRecyclerView()
        observeViewModel()

        super.onCreate(savedInstanceState)


    }

    private fun setupRecyclerView() {
        mBinding.mRecyclerView.apply {


            adapter = ReviewBindingAdapter(mViewModel,editMode ?: false).apply {

                onRemoveEvent.subscribe {
                    mViewModel.removeProblem(it)
                }

                onBlackListEvent.subscribe {
                    val builder = AlertDialog.Builder(this@ReviewProblemActivity)
                    val dialogView = layoutInflater.inflate(R.layout.basic_dialog, null)
                    //dialogView.basicDialogTitle.text = "이 문제를 블랙리스트에 추가하시겠습니까?"
                    builder.setView(dialogView)
                        .setPositiveButton("확인") { dialogInterface, i ->
                            //mViewModel.setBlackList(it)
                            mViewModel.removeProblem(it)
                        }
                        .setNegativeButton("취소") { dialogInterface, i ->
                            /* 취소일 때 아무 액션이 없으므로 빈칸 */
                        }
                        .show()

                }
            }
        }

    }

    private fun observeViewModel() {

        mViewModel.apply {
            clickConfirm.observeOnce(this@ReviewProblemActivity) {
                val problemListArgs = ProblemListArgs(
                    problems = problemList.value ?: listOf()
                )
                val intent = Intent()
                intent.putExtra(ARG_PROBLEM_LIST,problemListArgs)
                setResult(Navigator.RESULT_REVIEW_PROBLEM_CODE,intent)
                finish()

                Toast.makeText(this@ReviewProblemActivity,"문제 검토를 완료했습니다", Toast.LENGTH_SHORT).show()
            }
            blackListCompleted.observeOnce(this@ReviewProblemActivity){
                Toast.makeText(this@ReviewProblemActivity,"문제를 블랙리스트에 추가하였습니다", Toast.LENGTH_SHORT).show()
            }
        }

    }


}
