package com.math.firstMaker.views.makeCollection

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.math.firstMaker.args.ProblemListArgs
import com.math.firstMaker.common.Once
import com.math.firstMaker.common.addSchedulers
import com.math.firstMaker.common.disposedBy
import com.math.firstMaker.repository.ExamRepository
import com.math.firstMaker.repository.WorkBookRepository
import com.math.firstMaker.repository.WorkPaperRepository
import com.math.firstMaker.debugE
import io.reactivex.disposables.CompositeDisposable

class SetInfoViewModel (
    private val args : ProblemListArgs,
    private val type : String,
    private val workPaperRepository: WorkPaperRepository,
    private val workBookRepository: WorkBookRepository,
    private val examRepository: ExamRepository
) : ViewModel(){

    private val TAG = "SetInfoViewModel"

    private val compositeDisposable = CompositeDisposable()


    val title : MutableLiveData<String> = MutableLiveData()
    val timeLimit : MutableLiveData<String> = MutableLiveData()

    val publishSuccess : MutableLiveData<Once<Unit>> = MutableLiveData()
    val errorInvoked : MutableLiveData<Once<Throwable>> = MutableLiveData()


    fun onClickPublish () {

        debugE(TAG,"problem = " + args.problems.map{it.id})

        if ( type == "workpaper" ) {
//            workPaperRepository.createWorkPaper(
//                title = title.value ?: "제목없음",
//                problemIds = args.problems.map{it.id}
//            )
//                .addSchedulers()
//                .subscribe({
//                    publishSuccess.value = Once(Unit)
//                },{
//                    errorInvoked.value = Once(it)
//                    debugE(TAG,"workpaper = " + it.message)
//                }).disposedBy(compositeDisposable)
        }
        else if ( type == "workbook"){
//            workBookRepository.createWorkBook(
//                title = title.value ?: "제목없음",
//                problemIds = args.problems.map{it.id}
//            )
//                .addSchedulers()
//                .subscribe({
//                    publishSuccess.value = Once(Unit)
//                },{
//                    debugE(TAG, "workbook = "  + it.message)
//                    errorInvoked.value = Once(it)
//                }).disposedBy(compositeDisposable)
        }




    }


}