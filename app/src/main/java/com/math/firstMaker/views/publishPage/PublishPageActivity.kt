package com.math.firstMaker.views.publishPage

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView
import com.math.firstMaker.DateParserUtil
import com.math.firstMaker.R
import com.math.firstMaker.common.disposedBy
import com.math.firstMaker.common.increment
import com.math.firstMaker.common.observeOnce
import com.math.firstMaker.common.rxRepeatTimer
import com.math.firstMaker.databinding.ActivityPublishPageBinding
import com.math.firstMaker.debugE
import com.math.firstMaker.dialog.showTextDialog
import com.math.firstMaker.dialog.showTextDoubleDialog
import com.math.firstMaker.extensions.toggle
import com.math.firstMaker.model.Note
import com.math.firstMaker.navigation.DialogNavigator
import com.math.firstMaker.navigation.Navigator
import io.reactivex.disposables.CompositeDisposable
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.core.parameter.parametersOf


class PublishPageActivity : AppCompatActivity() {


    private val TAG = PublishPageActivity::class.java.simpleName

    private val timeDisposable = CompositeDisposable()
    /**
     * Binding Instance
     */
    private lateinit var mBinding: ActivityPublishPageBinding

    /**
     *  ViewModel Instance
     */

    private val navigator: Navigator by inject()

    private val dialogNavigator : DialogNavigator by inject()

    private val dateParserUtil : DateParserUtil by inject()
    private lateinit var mViewModel: PublishPageViewModel

    private var previousNumber = -1

    private var isScrolling = false


    private val compositeDisposable = CompositeDisposable()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        mBinding = ActivityPublishPageBinding.inflate(LayoutInflater.from(this))


        mViewModel = getViewModel {
            parametersOf(
                intent?.getStringExtra(ARG_TYPE),
                intent?.getIntExtra(ARG_ID,0)
            )
        }

        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)

        mBinding.lifecycleOwner = this
        mBinding.vm = mViewModel


        setContentView(mBinding.root)

        setupRecyclerView()
        observeViewModel()
        setupTimer()

    }

    private fun setupTimer() {

    }


    private fun setupRecyclerView() {
        mBinding.mNumRecyclerView.apply {
            adapter = ProblemNumAdapter(this@PublishPageActivity).apply {


                onClickEvent.subscribe {
                    isScrolling = false

                    val smoothScroller = object : LinearSmoothScroller(context) {
                        override fun getVerticalSnapPreference(): Int {
                            return SNAP_TO_START
                        }
                    }
                    smoothScroller.targetPosition = it
                    mBinding.mProblemRecyclerView.layoutManager?.startSmoothScroll(smoothScroller)
                    mViewModel.curProblemNumber.value = it
                }
            }

        }

        mBinding.mProblemRecyclerView.apply {
            adapter = ProblemBindingAdapter(this@PublishPageActivity).apply {


                onClickStarEvent.subscribe {
                    debugE(TAG, "WOW")

                    if (it.first.isGreenStarClicked.get()) Toast.makeText(this@PublishPageActivity,"${it.second + 1 }번 문제를 별표 체크하였습니다",Toast.LENGTH_SHORT).show()
                    else Toast.makeText(this@PublishPageActivity,"${it.second + 1 }번 문제를 별표 해제하였습니다",Toast.LENGTH_SHORT).show()

                }.disposedBy(compositeDisposable)

                val linearLayoutManager = LinearLayoutManager(context)
                layoutManager = linearLayoutManager

                addOnScrollListener(object : RecyclerView.OnScrollListener() {
                    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                        if (isScrolling)
                            mViewModel.curProblemNumber.value = linearLayoutManager.findLastVisibleItemPosition()
                    }


                })
                addOnItemTouchListener(object : RecyclerView.OnItemTouchListener {
                    override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {
                        isScrolling = true
                    }

                    override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
                        isScrolling = true
                        return false
                    }

                    override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {

                    }

                })

            }

        }

    }

    private fun observeViewModel() {

        mViewModel.apply {

            clickBack.observeOnce(this@PublishPageActivity) {
                onBackPressed()
            }

            clickScoring.observeOnce(this@PublishPageActivity) {
                onBackPressed()
            }
            clickCurrentProblem.observe(this@PublishPageActivity) {
                if (it == true) {
                    mBinding.mNumRecyclerView.visibility = View.GONE
                } else {
                    mBinding.mNumRecyclerView.visibility = View.VISIBLE
                }
            }
            curProblemNumber.observe(this@PublishPageActivity) {

                if ((it != -1 && previousNumber != it ) || ( previousNumber == -1 && it == 0)) {

                    debugE(TAG, "curPosition = " + it)
                    mBinding.mNumRecyclerView.scrollToPosition(it)

                    (mBinding.mNumRecyclerView.adapter as? ProblemNumAdapter)?.run {
                        curProblem = it
                        notifyDataSetChanged()
                    }

                    timeDisposable.clear()

                    rxRepeatTimer(100L) { timer ->
                        notes.value?.get(it)?.spendingTime?.increment()

                        if ( type == "시험") {
                            timeLimit.value = timeLimit.value?.minus(1)

                            if ( (timeLimit.value ?: 0 ) <= 0 && onLoadCompleted.value == true) {

                                Toast.makeText(this@PublishPageActivity,"제한 시간이 다되었습니다",Toast.LENGTH_SHORT).show()
                                finish()
                                timeDisposable.clear()
                            }
                        }

                        spendingTime.value = spendingTime.value?.plus(1)

                        if ( type == "시험") mBinding.txtSpendingTime.text = dateParserUtil.calculateTime(timeLimit.value ?: 0)
                        else mBinding.txtSpendingTime.text = dateParserUtil.calculateTime(spendingTime.value ?: 0)
                    }.disposedBy(timeDisposable)



                    previousNumber = it

                    //curProblem.value = ( problems.value ?: listOf())[it]
                }

            }

            problems.observe(this@PublishPageActivity) {

            }

            curProblem.observe(this@PublishPageActivity) {
//                (mBinding.mNumRecyclerView.adapter as? ProblemNumAdapter)?.run {
//                    if (it.submit == "") isSubmitted = false
//                    else if (it.submit != "" ) isSubmitted = true
//
//                    notifyDataSetChanged()
//
//                }

            }
            onLoadCompleted.observe(this@PublishPageActivity){
                if ( it ) {
                    problems.value = notes.value?.map { it.problem!! }
                }
            }
            errorInvoked.observeOnce(this@PublishPageActivity){
                debugE(TAG, it)
            }
        }
    }

    override fun onBackPressed() {

        if ( mViewModel.type == "시험") {
            showTextDoubleDialog(
                titleResource = R.string.common_notification,
                bodyResource = R.string.txt_hoxy_close_exam,
                onPositive = {
                    super.onBackPressed()
                },
                onNegative = {

                }
            )
        }
        else {
            super.onBackPressed()
        }
    }
    override fun onDestroy() {
        super.onDestroy()

        compositeDisposable.clear()
        timeDisposable.clear()

        mViewModel.onScoring()

    }


    companion object {

        private val TAG = PublishPageActivity::class.java.simpleName
        private const val ARG_TYPE = "ARG_TYPE"
        private const val ARG_ID = "ARG_ID"

        fun newIntent(
            context: Context,
            type : String,
            id : Int
        ): Intent {
            return Intent(context, PublishPageActivity::class.java).apply {
                putExtra(ARG_TYPE, type )
                putExtra(ARG_ID , id)
            }
        }
    }
}
