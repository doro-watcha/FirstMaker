package com.math.firstMaker.views.workBook

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.math.firstMaker.common.Once
import com.math.firstMaker.common.addSchedulers
import com.math.firstMaker.common.disposedBy
import com.math.firstMaker.debugE
import com.math.firstMaker.model.*
import com.math.firstMaker.repository.AuthRepository
import com.math.firstMaker.repository.ChapterRepository
import com.math.firstMaker.repository.PublishRepository
import com.math.firstMaker.repository.WorkBookRepository
import io.reactivex.disposables.CompositeDisposable
import org.koin.core.KoinComponent


/**
 * created By DORO 2020-02-28
 */

class WorkBookViewModel(
    private val workBookRepository: WorkBookRepository,
    private val publishRepository: PublishRepository,
    private val authRepository: AuthRepository,
    private val chapterRepository: ChapterRepository
) : ViewModel(), KoinComponent {

    private val TAG = WorkBookViewModel::class.java.simpleName

    val compositeDisposable = CompositeDisposable()

    val workBooks: MutableLiveData<List<WorkBook>?> = MutableLiveData(listOf())
    val myWorkBooks: MutableLiveData<List<WorkBook>?> = MutableLiveData(listOf())

    val curWorkBook: MutableLiveData<WorkBook> = MutableLiveData()
    val curBigChapter: MutableLiveData<BigChapter> = MutableLiveData()
    val curUser : MutableLiveData<User> = MutableLiveData()
    val bigChapterList: MutableLiveData<List<WorkBookBigChapter>> = MutableLiveData()

    val workBookMiddleChapters: MutableLiveData<List<WorkBookMiddleChapter>> = MutableLiveData()

    val currentPage: MutableLiveData<Int> = MutableLiveData(0)


    val clickMyWorkBook: MutableLiveData<Once<Unit>> = MutableLiveData()
    val clickWorkBook: MutableLiveData<Once<Unit>> = MutableLiveData()

    val clickAddWorkBook : MutableLiveData<Once<Unit>> = MutableLiveData()

    val buyChapterCompleted: MutableLiveData<Once<Unit>> = MutableLiveData()
    val onWorkBookLoadCompleted: MutableLiveData<Once<Unit>> = MutableLiveData()
    val onLoadCompleted: MutableLiveData<Once<Unit>> = MutableLiveData()
    val onLoadFailed: MutableLiveData<Once<Throwable>> = MutableLiveData()
    val errorInvoked: MutableLiveData<Once<Throwable>> = MutableLiveData()

    init {

        curUser.value = authRepository.curUser.value

        if (authRepository.curUser.value?.type == "student") {
            listMyWorkBook()
        }

        listWorkBook()
    }

    fun listMyWorkBook() {

        workBookRepository.listMyWorkBooks(
            studentId = authRepository.curUser.value?.student?.id ?: 0
        )
            .addSchedulers()
            .subscribe({
                debugE(TAG, "My WorkBook Completed")
                debugE(TAG, it.size)
                myWorkBooks.value = it.distinctBy { it.id }
            }, {
                errorInvoked.value = Once(it)
            }).disposedBy(compositeDisposable)

    }

    private fun listWorkBook() {

        workBookRepository.listWorkBook()
            .addSchedulers()
            .subscribe({
                debugE(TAG, "WorkBook Completed")
                debugE(TAG, it.size)
                workBooks.value = it
            }, {
                errorInvoked.value = Once(it)
            }).disposedBy(compositeDisposable)
    }

    fun getWorkBook(workBookId: Int, myWorkBook: Boolean? = false) {

        workBookRepository.getWorkBook(workBookId)
            .addSchedulers()
            .subscribe({
                debugE(TAG, "Find ONe WorkBook")
                curWorkBook.value = it
                currentPage.value = 1
                bigChapterList.value = it.subject.bigChapters?.map {
                    WorkBookBigChapter(it, ObservableBoolean(false))
                }


                workBookRepository.listMyChapterList(
                    authRepository.curUser.value?.student?.id ?: 0,
                    curWorkBook.value?.id ?: 0
                )
                    .addSchedulers()
                    .subscribe({
                        debugE(TAG, "LISTMYCHAPTER")
                        debugE(TAG, it.map { it.id })

                        bigChapterList.value?.forEach { workBookBigChapter ->

                            if (it.contains(workBookBigChapter.bigChapter)) {
                                workBookBigChapter.isMyChapter.set(true)
                            }
                        }

                        if (myWorkBook == true) {
                            bigChapterList.value =
                                bigChapterList.value?.filter { workBookBigChapter ->
                                    workBookBigChapter.isMyChapter.get()
                                }
                        }
                    }, {
                        errorInvoked.value = Once(it)
                    }).disposedBy(compositeDisposable)

            }, {
                errorInvoked.value = Once(it)
            }).disposedBy(compositeDisposable)
    }

    fun buyChapter(workBookBigChapter: WorkBookBigChapter) {


        workBookRepository.buyBigChapter(
            studentId = authRepository.curUser.value?.student?.id ?: 0,
            workBookId = curWorkBook.value?.id ?: 0,
            bigChapterId = workBookBigChapter.bigChapter.id
        )
            .addSchedulers()
            .subscribe({
                buyChapterCompleted.value = Once(Unit)
                workBookBigChapter.isMyChapter.set(true)
            }, {
                errorInvoked.value = Once(it)
            }).disposedBy(compositeDisposable)
    }

    fun listMiddleChapters(bigChapter: BigChapter) {

        debugE(TAG, "내가부르려고하는 대단원!")
        debugE(TAG, bigChapter.id)

        chapterRepository.getMiddleChapterList(bigChapter.id)
            .addSchedulers()
            .subscribe({
                debugE(TAG, it.size)
                workBookMiddleChapters.value = it.map { middleChapter ->
                    WorkBookMiddleChapter(
                        middleChapter,
                        ObservableBoolean(false)
                    )
                }
                currentPage.value = 2
            }, {
                errorInvoked.value = Once(it)
            }).disposedBy(compositeDisposable)


    }

    fun onClickMyWorkBook(item: WorkBook) {
        clickMyWorkBook.value = Once(Unit)
    }

    fun onClickWorkBook(item: WorkBook) {
        clickWorkBook.value = Once(Unit)
    }

    fun navigatePage(position: Int) {
        currentPage.value = position
    }

    fun onClickAddWorkBook() {
        clickAddWorkBook.value = Once(Unit)
    }

}

