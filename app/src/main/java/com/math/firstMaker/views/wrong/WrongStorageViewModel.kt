package com.math.firstMaker.views.wrong

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.math.firstMaker.broadcast.Broadcast
import com.math.firstMaker.common.Once
import com.math.firstMaker.common.addSchedulers
import com.math.firstMaker.common.disposedBy
import com.math.firstMaker.debugE
import com.math.firstMaker.model.*
import com.math.firstMaker.model.Collection
import com.math.firstMaker.repository.CollectionRepository
import com.math.firstMaker.repository.NoteRepository
import com.math.firstMaker.repository.WorkPaperRepository
import io.reactivex.disposables.CompositeDisposable
import java.text.SimpleDateFormat
import java.util.*

class WrongStorageViewModel(
    private val noteRepository: NoteRepository,
    private val workPaperRepository: WorkPaperRepository
): ViewModel() {

    private val TAG = WrongStorageViewModel::class.java.simpleName
    private val compositeDisposable = CompositeDisposable()
    private val chapterDisposable = CompositeDisposable()


    val wrongTypes: MutableLiveData<List<WrongType>> = MutableLiveData()
    val wrongType: MutableLiveData<WrongType> = MutableLiveData()

    val start: MutableLiveData<Calendar> = MutableLiveData(Calendar.getInstance())
    val end: MutableLiveData<Calendar> = MutableLiveData(Calendar.getInstance())

    val title : MutableLiveData<String> = MutableLiveData()
    val currentPage: MutableLiveData<Int> = MutableLiveData()

    val subject: MutableLiveData<Subject> = MutableLiveData()

    val notes: MutableLiveData<List<Note>> = MutableLiveData()

    val bigChapters = MediatorLiveData<List<BigChapter>>().apply {

        addSource(notes) {
            it.forEach { note ->

                // for문 돌리는 note의 bigChapter와 내가 가지고 있는 List<BigChapter>가 같으면
                val bigChapter = this.value?.find {
                    note.problem?.bigChapter!!.id == it.id
                }

                if (bigChapter == null) {

                    val _bigChapter = note.problem!!.bigChapter
                    _bigChapter.middleChapters = listOf(note.problem.middleChapter)
                    _bigChapter.middleChapters!![0].smallChapters =
                        listOf(note.problem.smallChapter)

                    if (this.value?.size ?: 0 > 0) this.value =
                        this.value!! + listOf(_bigChapter)
                    else this.value = listOf(_bigChapter)

                } else {

                    val middleChapter = bigChapter.middleChapters?.find {
                        note.problem!!.middleChapter.id == it.id
                    }

                    if (middleChapter == null) {

                        val _middleChapter = note.problem!!.middleChapter
                        _middleChapter.smallChapters = listOf(note.problem.smallChapter)

                        if (bigChapter.middleChapters?.size ?: 0 > 0) bigChapter.middleChapters =
                            bigChapter.middleChapters?.plus(listOf(_middleChapter))
                        else bigChapter.middleChapters = listOf(_middleChapter)

                    } else {
                        val smallChapter = middleChapter.smallChapters?.find {
                            note.problem!!.smallChapter.id == it.id
                        }
                        if (smallChapter == null) {
                            if (bigChapter.middleChapters?.size ?: 0 > 0) middleChapter.smallChapters =
                                middleChapter.smallChapters?.plus(listOf(note.problem!!.smallChapter))
                            else middleChapter.smallChapters = listOf(note.problem!!.smallChapter)
                        } else {
                            smallChapter.numberOfNotes++
                        }
                    }

                }
            }

            debugE(TAG, this.value?.size)
        }

    }

    val clickDatePicker: MutableLiveData<Once<Unit>> = MutableLiveData()
    val clickBeforeStep: MutableLiveData<Once<Unit>> = MutableLiveData()
    val clickStartTest: MutableLiveData<Once<Collection>> = MutableLiveData()

    val collectionCreated : MutableLiveData<Once<Unit>> = MutableLiveData()
    val onLoadCompleted : MutableLiveData<Once<Unit>> = MutableLiveData()
    val errorInvoked: MutableLiveData<Once<Throwable>> = MutableLiveData()

    init {

        start.value = Calendar.getInstance()
        end.value = Calendar.getInstance()

        wrongTypes.value = listOf(
            WrongType(0, "틀린 문제", "평균 정답률 32%", "wrong"),
            WrongType(1, "오래 걸린 문제", "평균 정답률 24%", "long"),
            WrongType(2, "별표 문제", "평균 정답률 16%", "star")
        )

    }

    fun listNotes(wrongType: WrongType) {


        val startDayFormat = SimpleDateFormat("yyyy-MM-dd 00:00:00")
        val endDayFormat = SimpleDateFormat("yyyy-MM-dd 23:59:59")
        val startDate = startDayFormat.format(start.value?.time ?: "")
        val endDate = endDayFormat.format(end.value?.time ?: "")

        noteRepository.listSpecialNotes(
            subject = "고등수학 상",
            type = wrongType.type,
            startDate = startDate,
            endDate = endDate
        )
            .addSchedulers()
            .subscribe({
                notes.value = it
                onLoadCompleted.value = Once(Unit)
                debugE(TAG, it.map{it.id})
                debugE(TAG, "note size " + it.size)
            }, {
                debugE(TAG, it)
                errorInvoked.value = Once(it)
            }).disposedBy(compositeDisposable)


    }


    fun onClickDatePicker() {

        clickDatePicker.value = Once(Unit)
    }

    fun onClickBeforeStep() {
        clickBeforeStep.value = Once(Unit)
    }


    fun cleanViewModel() {

        currentPage.value = 0
    }

    fun onClickStartTest() {

        val smallChapterList = bigChapters.value?.flatMap{
            it.middleChapters?.toList() ?: listOf()
        }?.flatMap{
            it.smallChapters?.toList() ?: listOf()
        }?.filter{
            it.isSelected.get()
        }?: listOf()

        debugE(TAG, smallChapterList.size)

        if (smallChapterList.isNotEmpty()) {

            val problemIds = notes.value?.filter {
                smallChapterList.map{it.id}.contains(it.problem?.smallChapter?.id)
            }?.map {
                it.problem?.id ?: 0
            } ?: listOf()

            workPaperRepository.createWorkPaper(
                title.value ?: "",
                problemIds,
                smallChapterList.size ,
                smallChapterList[0].name

            )
                .addSchedulers()
                .subscribe({
                    Broadcast.paperCreatedBroadcast.onNext(Unit)
                    collectionCreated.value = Once(Unit)
                }, {
                    errorInvoked.value = Once(it)
                }).disposedBy(compositeDisposable)
        }

    }

    override fun onCleared() {
        super.onCleared()

        compositeDisposable.clear()
        chapterDisposable.clear()
    }
}