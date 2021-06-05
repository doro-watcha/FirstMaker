package com.math.firstMaker.views.makeCollection.problemSet

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.math.firstMaker.common.Once
import com.math.firstMaker.common.addSchedulers
import com.math.firstMaker.common.disposedBy
import com.math.firstMaker.debugE
import com.math.firstMaker.model.Problem
import com.math.firstMaker.model.ProblemSet
import com.math.firstMaker.repository.AuthRepository
import com.math.firstMaker.repository.ProblemRepository
import io.reactivex.disposables.CompositeDisposable


/**
 * created By DORO 1/14/21
 */

class ProblemSetViewModel(
    private val problemSet: ProblemSet,
    val problemRepository: ProblemRepository,
    val authRepository: AuthRepository
) : ViewModel() {

    private val TAG = ProblemSetViewModel::class.java.simpleName

    private val compositeDisposable = CompositeDisposable()

    val problems: MutableLiveData<List<Problem>> = MutableLiveData(problemSet.problems)


    val clickConfirm : MutableLiveData<Once<Unit>> = MutableLiveData()
    val noProblemSearch : MutableLiveData<Once<Unit>> = MutableLiveData()
    val blackListRegisterCompleted : MutableLiveData<Once<Unit>> = MutableLiveData()
    val replaceCompleted : MutableLiveData<Once<Pair<Problem,Problem>>> = MutableLiveData()
    val errorInvoked: MutableLiveData<Once<Throwable>> = MutableLiveData()

    var minLevel : Int = 1
    var maxLevel : Int = 5

    fun registerBlackList(problem: Problem) {


        problemRepository.registerBlackList(problem.id, authRepository.curUser.value?.teacher?.id ?: 0)
            .addSchedulers()
            .subscribe({
                blackListRegisterCompleted.value = Once(Unit)
                replaceProblem(problem)
            }, {
                errorInvoked.value = Once(it)
            }).disposedBy(compositeDisposable)
    }

    fun replaceProblem(problem: Problem) {

        problemRepository.listReplaceProblem(
            problem.smallChapter.id,
            1,
            problems.value?.map{it.id} ?: listOf(),
            minLevel,
            maxLevel
        )
            .addSchedulers()
            .subscribe({
                debugE(TAG, it)

                if (it.isEmpty()) noProblemSearch.value = Once(Unit)
                else {

                    problems.value = problems.value?.minus(problem)
                    problems.value = (problems.value ?: listOf()) + it

                    replaceCompleted.value = Once(Pair(problem,it[0]))
                }

            },{
               errorInvoked.value = Once(it)
            }).disposedBy(compositeDisposable)

    }

    fun onClickConfirm() {
        clickConfirm.value = Once(Unit)
    }

}