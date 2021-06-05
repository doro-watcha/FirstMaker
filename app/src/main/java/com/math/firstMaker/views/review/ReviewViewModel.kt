package com.math.firstMaker.views.review

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.math.firstMaker.args.ProblemListArgs
import com.math.firstMaker.common.Once
import com.math.firstMaker.common.addSchedulers
import com.math.firstMaker.common.disposedBy
import com.math.firstMaker.model.Problem
import com.math.firstMaker.repository.ProblemRepository
import io.reactivex.disposables.CompositeDisposable

class ReviewViewModel (
    private val args : ProblemListArgs,
    private val problemRepository: ProblemRepository
) : ViewModel() {

    val compositeDisposable = CompositeDisposable()
    val problemList : MutableLiveData<List<Problem>> = MutableLiveData()

    val clickConfirm : MutableLiveData<Once<Unit>> = MutableLiveData()
    val blackListCompleted : MutableLiveData<Once<Unit>> = MutableLiveData()
    val errorInvoked : MutableLiveData<Once<Throwable>> = MutableLiveData()

    init {
        problemList.value = args.problems
    }

//
//    fun setBlackList ( problem : Problem ){
//        problemRepository.blackListProblem(problem.id)
//            .addSchedulers()
//            .subscribe({
//                blackListCompleted.value = Once(Unit)
//            },{
//
//            }).disposedBy(compositeDisposable)
//    }

    fun onClickConfirm () {
        clickConfirm.value = Once(Unit)
    }

    fun removeProblem ( problem : Problem) {
        problemList.value = (problemList.value ?: listOf()) - problem
    }

}