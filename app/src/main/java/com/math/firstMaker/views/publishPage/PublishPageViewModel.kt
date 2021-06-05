package com.math.firstMaker.views.publishPage

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.math.firstMaker.broadcast.Broadcast
import com.math.firstMaker.common.Once
import com.math.firstMaker.common.addSchedulers
import com.math.firstMaker.common.disposedBy
import com.math.firstMaker.debugE
import com.math.firstMaker.model.Note
import com.math.firstMaker.model.Problem
import com.math.firstMaker.repository.ExamRepository
import com.math.firstMaker.repository.HomeworkRepository
import com.math.firstMaker.repository.NoteRepository
import com.math.firstMaker.repository.WorkPaperRepository
import io.reactivex.disposables.CompositeDisposable

class PublishPageViewModel(
    val type: String,
    val id: Int,
    val workPaperRepository: WorkPaperRepository,
    val homeworkRepository: HomeworkRepository,
    val examRepository: ExamRepository,
    val noteRepository: NoteRepository
): ViewModel() {

    private val TAG = PublishPageViewModel::class.java.simpleName

    private val compositeDisposable = CompositeDisposable()

    val notes: MutableLiveData<List<Note>> = MutableLiveData()
    val problems: MutableLiveData<List<Problem>> = MutableLiveData()
    val spendingTime: MutableLiveData<Int> = MutableLiveData()
    val timeLimit : MutableLiveData<Int> = MutableLiveData()
    val curProblem: MutableLiveData<Problem> = MutableLiveData()
    val curProblemNumber: MutableLiveData<Int> = MutableLiveData()

    val clickBack: MutableLiveData<Once<Unit>> = MutableLiveData()
    val clickScoring: MutableLiveData<Once<Unit>> = MutableLiveData()
    val clickCurrentProblem: MutableLiveData<Boolean> = MutableLiveData(false)
    // end region

    val onLoadCompleted: MutableLiveData<Boolean> = MutableLiveData()
    val errorInvoked: MutableLiveData<Once<Throwable>> = MutableLiveData()

    init {

        getNoteList()
        curProblemNumber.value = 0


        debugE(TAG, id)
        debugE(TAG, type)

    }

    private fun getNoteList() {

        when (type) {

            "숙제" -> {
                homeworkRepository.getHomework(id)
                    .addSchedulers()
                    .subscribe({
                        notes.value = it.notes
                        spendingTime.value = it.spendingTime
                        onLoadCompleted.value = true
                    },{
                        errorInvoked.value = Once(it)
                    }).disposedBy(compositeDisposable)

            }
            "시험" -> {

                examRepository.getExam(id)
                    .addSchedulers()
                    .subscribe({
                        notes.value = it.notes
                        timeLimit.value = it.timeLimit * 600
                        spendingTime.value = 0
                        onLoadCompleted.value = true
                    },{
                        errorInvoked.value = Once(it)
                    }).disposedBy(compositeDisposable)

            }

            "문제집" -> {

            }
            "문제지" -> {

                workPaperRepository.getWorkPaper(id)
                    .addSchedulers()
                    .subscribe({
                        notes.value = it.notes
                        spendingTime.value = it.spendingTime
                        onLoadCompleted.value = true
                    },{
                        errorInvoked.value = Once(it)
                    }).disposedBy(compositeDisposable)
            }
            else -> {

            }

        }
    }


    fun onClickBack() {
        clickBack.value = Once(Unit)
    }

    fun onClickCurrentProblem() {
        clickCurrentProblem.value = !(clickCurrentProblem.value ?: false)

    }

    fun onClickScoring() {
        clickScoring.value = Once(Unit)
    }

    fun onScoring() {

        val noteIdList = notes.value?.map { it.id }
        val submitList = notes.value?.map { it.submit.get() ?: "" }
        val spendingTimeList = notes.value?.map { it.spendingTime.get() }
        val greenStarClickedList = notes.value?.map {it.isGreenStarClicked.get()}

        noteRepository.scoringNote(
            submitList,
            noteIdList,
            spendingTimeList,
            greenStarClickedList,
            spendingTime.value ?: 0,
            type,
            id
        )
            .addSchedulers()
            .subscribe({
                debugE(TAG, "success")

                Broadcast.scoringCompletedBroadcast.onNext(it)
            }, {
                errorInvoked.value = Once(it)
            }).disposedBy(compositeDisposable)

    }
}