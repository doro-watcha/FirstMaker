package com.math.firstMaker.views.home.detail

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView
import com.math.firstMaker.common.disposedBy
import com.math.firstMaker.common.observeOnce
import com.math.firstMaker.databinding.ActivityNoteDetailBinding
import com.math.firstMaker.debugE
import com.math.firstMaker.views.publishPage.ProblemNumAdapter
import io.reactivex.disposables.CompositeDisposable
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class NoteDetailActivity : AppCompatActivity() {

    private val TAG = NoteDetailActivity::class.java.simpleName

    private val compositeDisposable = CompositeDisposable()


    private lateinit var mBinding : ActivityNoteDetailBinding
    private lateinit var mViewModel : NoteDetailViewModel

    private var position = 0

    private var previousNumber = -1

    private var isScrolling = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityNoteDetailBinding.inflate(LayoutInflater.from(this))

        position = intent?.getIntExtra(ARG_POSITION,0) ?: 0

        mViewModel = getViewModel{
            parametersOf(
                intent?.getIntExtra(ARG_COLLECTION_ID,0),
                intent?.getStringExtra(ARG_COLLECTION_TYPE)
            )
        }

        mBinding.vm = mViewModel
        mBinding.lifecycleOwner = this


        setContentView(mBinding.root)

        observeViewModel()
        setupRecyclerView()
    }

    private fun setupRecyclerView() {

        mBinding.mRecyclerView.apply {

            adapter = NoteListAdapter(this@NoteDetailActivity)

            val linearLayoutManager = LinearLayoutManager(context)
            layoutManager = linearLayoutManager

            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    if (isScrolling) mViewModel.curProblemNumber.value = linearLayoutManager.findLastVisibleItemPosition()
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

        mBinding.mNumRecyclerView.apply {

            adapter = ProblemNumAdapter(this@NoteDetailActivity).apply {

                onClickEvent.subscribe{

                    isScrolling = false

                    val smoothScroller = object : LinearSmoothScroller(context) {
                        override fun getVerticalSnapPreference(): Int {
                            return SNAP_TO_START
                        }
                    }
                    smoothScroller.targetPosition = it
                    mBinding.mRecyclerView.layoutManager?.startSmoothScroll(smoothScroller)
                    mViewModel.curProblemNumber.value = it
                }.disposedBy(compositeDisposable)
            }
        }
    }

    private fun observeViewModel() {

        mViewModel.apply {

            curProblemNumber.observe(this@NoteDetailActivity){

                if ((it != -1 && previousNumber != it ) || ( previousNumber == -1 )) {
                    debugE(TAG, "curPosition = " + it)
                    mBinding.mNumRecyclerView.scrollToPosition(it)

                    (mBinding.mNumRecyclerView.adapter as? ProblemNumAdapter)?.run {
                        curProblem = it
                        notifyDataSetChanged()
                    }

                    previousNumber = it
                }
            }

            onLoadCompleted.observeOnce(this@NoteDetailActivity){
                mBinding.mRecyclerView.scrollToPosition(position)
                curProblemNumber.value = position
            }

            errorInvoked.observeOnce(this@NoteDetailActivity) {
                debugE(TAG, it)
            }
        }
    }

    companion object {

        private const val ARG_COLLECTION_ID = "ARG_COLLECTION_ID"
        private const val ARG_COLLECTION_TYPE = "ARG_COLLECTION_TYPE"
        private const val ARG_POSITION = "ARG_POSITION"

        fun newIntent (context : Context, collectionId : Int , type : String ,position: Int ) : Intent {

            val intent = Intent ( context, NoteDetailActivity::class.java)
            intent.putExtra(ARG_COLLECTION_ID,collectionId)
            intent.putExtra(ARG_COLLECTION_TYPE, type)
            intent.putExtra(ARG_POSITION, position)
            return intent
        }
    }
}