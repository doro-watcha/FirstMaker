package com.math.firstMaker.views.home.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.math.firstMaker.common.Once
import com.math.firstMaker.common.addSchedulers
import com.math.firstMaker.common.disposedBy
import com.math.firstMaker.debugE
import com.math.firstMaker.model.Note
import com.math.firstMaker.repository.ExamRepository
import com.math.firstMaker.repository.HomeworkRepository
import com.math.firstMaker.repository.NoteRepository
import com.math.firstMaker.repository.WorkPaperRepository
import io.reactivex.disposables.CompositeDisposable


/**
 * created By DORO 1/9/21
 */

class NoteDetailViewModel (
    val collectionId : Int,
    val type : String,
    val workPaperRepository: WorkPaperRepository,
    val homeworkRepository: HomeworkRepository,
    val examRepository: ExamRepository
): ViewModel(){

    private val TAG = NoteDetailViewModel::class.java.simpleName

    private val compositeDisposable = CompositeDisposable()

    val notes : MutableLiveData<List<Note>> = MutableLiveData()


    val curProblemNumber : MutableLiveData<Int> = MutableLiveData()
    val onLoadCompleted : MutableLiveData<Once<Unit>> = MutableLiveData()
    val errorInvoked : MutableLiveData<Once<Throwable>> = MutableLiveData()


    init {

        getNoteList()
    }

    private fun getNoteList () {

        when ( type ) {

            "문제지" -> {
                workPaperRepository.getWorkPaper(collectionId)
                    .addSchedulers()
                    .subscribe({
                        notes.value = it.notes
                        onLoadCompleted.value = Once(Unit)
                    },{
                        errorInvoked.value = Once(it)
                    }).disposedBy(compositeDisposable)

            }

            "시험" -> {

                examRepository.getExam(collectionId)
                    .addSchedulers()
                    .subscribe({
                        notes.value = it.notes
                        onLoadCompleted.value = Once(Unit)
                    },{
                        errorInvoked.value = Once(it)
                    }).disposedBy(compositeDisposable)
            }


            "숙제" -> {

                homeworkRepository.getHomework(collectionId)
                    .addSchedulers()
                    .subscribe({
                        onLoadCompleted.value = Once(Unit)
                        notes.value = it.notes
                    },{
                        errorInvoked.value = Once(it)
                    }).disposedBy(compositeDisposable)
            }
        }

    }


}