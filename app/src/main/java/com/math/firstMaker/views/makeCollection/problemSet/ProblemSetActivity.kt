package com.math.firstMaker.views.makeCollection.problemSet

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.widget.Toast
import com.math.firstMaker.broadcast.Broadcast
import com.math.firstMaker.common.disposedBy
import com.math.firstMaker.common.observeOnce
import com.math.firstMaker.databinding.ActivityProblemSetBinding
import com.math.firstMaker.debugE
import com.math.firstMaker.model.Problem
import com.math.firstMaker.model.ProblemSet
import io.reactivex.disposables.CompositeDisposable
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class ProblemSetActivity : AppCompatActivity() {

    private val compositeDisposable = CompositeDisposable()

    private lateinit var mBinding : ActivityProblemSetBinding
    private lateinit var  mViewModel : ProblemSetViewModel

    private lateinit var problemSet : ProblemSet


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityProblemSetBinding.inflate(LayoutInflater.from(this))

        problemSet = intent.getParcelableExtra(ARG_PROBLEM_SET)!!

        debugE(TAG, "ProblemSetActivity OnCreate")

        mViewModel = getViewModel{
            parametersOf(
                problemSet
            )
        }

        mBinding.vm = mViewModel
        mBinding.lifecycleOwner = this

        setContentView(mBinding.root)

        mViewModel.minLevel = intent?.getIntExtra(ARG_MIN_LEVEL,0) ?: 0
        mViewModel.maxLevel = intent?.getIntExtra(ARG_MAX_LEVEL,0) ?: 0

        setupRecyclerView()
        observeViewModel()
    }

    private fun setupRecyclerView() {

        mBinding.mRecyclerView.apply {

            adapter = ProblemSetAdapter().apply{

                onBlackListEvent.subscribe{
                    mViewModel.registerBlackList(it)
                }.disposedBy(compositeDisposable)

                onReplaceEvent.subscribe{
                    mViewModel.replaceProblem(it)
                }.disposedBy(compositeDisposable)
            }
        }


    }
    private fun observeViewModel() {

        mViewModel.apply {

            blackListRegisterCompleted.observeOnce(this@ProblemSetActivity){

                Toast.makeText(this@ProblemSetActivity,"블랙리스트로 추가하였습니다",Toast.LENGTH_SHORT).show()
            }

            replaceCompleted.observeOnce(this@ProblemSetActivity){
                Toast.makeText(this@ProblemSetActivity,"문제를 대체하였습니다",Toast.LENGTH_SHORT).show()
                Broadcast.problemChangeBroadcast.onNext(Triple(it.first,it.second, problemSet))
            }

            noProblemSearch.observeOnce(this@ProblemSetActivity){
                Toast.makeText(this@ProblemSetActivity,"다른 문제가 없습니다. 그냥 푸셔야할듯..", Toast.LENGTH_SHORT).show()
            }

            clickConfirm.observeOnce(this@ProblemSetActivity){
                finish()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        compositeDisposable.clear()
    }

    companion object {

        private const val ARG_PROBLEM_SET = "ARG_PROBLEM_SET"
        private const val ARG_MIN_LEVEL = "ARG_MIN_LEVEL"
        private const val ARG_MAX_LEVEL = "ARG_MAX_LEVEL"

        private val TAG = ProblemSetActivity::class.java.simpleName
        fun newIntent (context: Context, problemSet : ProblemSet, minLevel : Int, maxLevel : Int ) : Intent {

            debugE(TAG, problemSet)

            val intent = Intent(context, ProblemSetActivity::class.java)
            intent.putExtra(ARG_PROBLEM_SET,problemSet)
            intent.putExtra(ARG_MIN_LEVEL,minLevel)
            intent.putExtra(ARG_MAX_LEVEL, maxLevel)

            return intent
        }
    }



}