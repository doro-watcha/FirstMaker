package com.math.firstMaker.views.makeCollection

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.math.firstMaker.CommonConst
import com.math.firstMaker.broadcast.Broadcast
import com.math.firstMaker.common.Once
import com.math.firstMaker.common.addSchedulers
import com.math.firstMaker.common.disposedBy
import com.math.firstMaker.debugE
import com.math.firstMaker.model.*
import com.math.firstMaker.repository.*
import io.reactivex.disposables.CompositeDisposable


class MakeCollectionViewModel(
    private val problemRepository: ProblemRepository,
    private val chapterRepository: ChapterRepository,
    private val workPaperRepository: WorkPaperRepository,
    private val homeworkRepository: HomeworkRepository,
    private val examRepository: ExamRepository,
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository

) : ViewModel() {

    private val TAG = MakeCollectionViewModel::class.java.simpleName

    private val compositeDisposable = CompositeDisposable()

    val currentPage: MutableLiveData<Int> = MutableLiveData(0)
    val curUser: MutableLiveData<User?> = MutableLiveData(authRepository.curUser.value)

    val subject: MutableLiveData<Subject?> = MutableLiveData()
    val bigChapters: MutableLiveData<List<BigChapter>> = MutableLiveData()

    val pickedSmallChapters: MutableLiveData<ArrayList<SmallChapter>> = MutableLiveData(ArrayList())

    val problems: MutableLiveData<List<Problem>> = MutableLiveData()
    val problemSetList : MutableLiveData<List<ProblemSet>> = MutableLiveData()

    val title: MutableLiveData<String> = MutableLiveData()


    val totalNumbers: MutableLiveData<Int> = MutableLiveData()
    val minLevel: MutableLiveData<Int> = MutableLiveData(1)
    val maxLevel : MutableLiveData<Int> = MutableLiveData(5)
    val source : MutableLiveData<String> = MutableLiveData()

    val isNotDuplicated : MutableLiveData<Boolean> = MutableLiveData()



    val clickGiveCollection: MutableLiveData<Once<Unit>> = MutableLiveData()
    val clickCourse: MutableLiveData<Once<Unit>> = MutableLiveData()
    val clickFirstStep: MutableLiveData<Once<Unit>> = MutableLiveData()
    val clickSourceDialog : MutableLiveData<Once<Unit>> = MutableLiveData()
    val onSaveCollection: MutableLiveData<Once<String>> = MutableLiveData()
    val errorInvoked: MutableLiveData<Once<Throwable>> = MutableLiveData()



    init {
        currentPage.value = 0


    }


    fun onPickCourse() {

        clickCourse.value = Once(Unit)
    }

    fun onClickNextStep() {
        if (currentPage.value == 0) {
            chapterRepository.getBigChapterList(subject.value?.id ?: 0)
                .addSchedulers()
                .subscribe({

                    currentPage.value = (currentPage.value ?: 0) + 1
                    bigChapters.value = it
                }, {

                }).disposedBy(compositeDisposable)
        } else {
            currentPage.value = (currentPage.value ?: 0) + 1
        }
    }

    fun onClickBeforeStep () {

        currentPage.value = (currentPage.value ?: 0 ) - 1
    }

    fun cleanViewModel() {

        currentPage.value = 0
    }

    fun onClickFirstStep() {

        clickFirstStep.value = Once(Unit)
    }

    fun getProblemList() {

        pickedSmallChapters.value.apply {

            problemRepository.findProblems(
                this?.map { it.id } ?: listOf(),
                this?.map { Integer.parseInt(it.numberOfProblems.get() ?: "0") } ?: listOf(),
                isNotDuplicated.value ?: false,
                minLevel.value ?: 0,
                maxLevel.value ?: 5
            )
                .addSchedulers()
                .subscribe({
                    debugE(TAG, it.problems.map{it.id})
                    problems.value = it.problems
                    problemSetList.value = it.problemSetList

                    currentPage.value = (currentPage.value ?: 0) + 1
                }, {
                    debugE(TAG, it)
                    errorInvoked.value = Once(it)
                }).disposedBy(compositeDisposable)

        }
    }


    fun onClickSaveCollection() {

        workPaperRepository.createWorkPaper(
            title.value ?: "",
            problems.value?.map { it.id } ?: listOf(),
            pickedSmallChapters.value?.size ?: 0,
            (pickedSmallChapters.value)?.get(0)?.name ?: "여러 단원")
            .addSchedulers()
            .subscribe({
                Broadcast.paperCreatedBroadcast.onNext(Unit)
                onSaveCollection.value = Once("문제지")
            }, {
                errorInvoked.value = Once(it)
            }).disposedBy(compositeDisposable)


    }

    fun onClickGiveCollection() {
        clickGiveCollection.value = Once(Unit)
    }

    fun onClickSourceDialog() {
        clickSourceDialog.value = Once(Unit)
    }



    fun clearData() {
        subject.value = null
        bigChapters.value = listOf()
        pickedSmallChapters.value = ArrayList()
        problems.value = listOf()
        title.value = null

    }
}





