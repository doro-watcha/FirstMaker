package com.math.firstMaker.views.home

import android.icu.text.DateFormat.DAY
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.math.firstMaker.common.Once
import com.math.firstMaker.common.addSchedulers
import com.math.firstMaker.common.disposedBy
import com.math.firstMaker.AppPreference
import com.math.firstMaker.CommonConst
import com.math.firstMaker.DateParserUtil
import com.math.firstMaker.debugE
import com.math.firstMaker.model.*
import com.math.firstMaker.model.Collection
import com.math.firstMaker.model.Date
import com.math.firstMaker.repository.*
import io.reactivex.disposables.CompositeDisposable
import org.koin.core.KoinComponent
import org.koin.core.inject
import java.util.*

class HomeViewModel(
    private val authRepository: AuthRepository,
    private val workPaperRepository: WorkPaperRepository,
    private val noteRepository: NoteRepository,
    private val homeworkRepository: HomeworkRepository,
    private val examRepository: ExamRepository,
    private val classRepository: ClassRepository
) : ViewModel(), KoinComponent {


    private val TAG: String = HomeViewModel::class.java.simpleName

    private val dateParserUtil: DateParserUtil by inject()
    val curUser : MutableLiveData<User> = MutableLiveData()


    val books : MutableLiveData<List<WorkBook>> = MutableLiveData()
    val exams : MutableLiveData<List<Exam>> = MutableLiveData()
    val homeworks : MutableLiveData<List<Homework>> = MutableLiveData()
    val papers : MutableLiveData<List<WorkPaper>> = MutableLiveData()
    val wrongPapers : MutableLiveData<List<WrongPaper>> = MutableLiveData()

    val curType : MutableLiveData<String> = MutableLiveData()
    val curTeacher : MutableLiveData<Teacher> = MutableLiveData()
    var curId = 0

    val curTitle : MutableLiveData<String> = MutableLiveData()

    val notes : MutableLiveData<List<Note>> = MutableLiveData()
    val correctRate : MutableLiveData<String> = MutableLiveData()
    val results : MutableLiveData<List<Note>> = MutableLiveData()
    val status : MutableLiveData<String> = MutableLiveData()
    val spendingTime : MutableLiveData<Int> = MutableLiveData()

    val getFullTime = MediatorLiveData<String>().apply {

        this.addSource(spendingTime){
            this.value = dateParserUtil.calculateTimeBySecond(it ?: 0)
        }
    }

    val currentPage : MutableLiveData<Int> = MutableLiveData(0)


    private val compositeDisposable = CompositeDisposable()

    val loadPaperCompleted : MutableLiveData<Boolean> = MutableLiveData()
    val loadExamCompleted : MutableLiveData<Boolean> = MutableLiveData()
    val loadHomeworkCompleted : MutableLiveData<Boolean> = MutableLiveData()
    val refreshCompleted : MutableLiveData<Once<Unit>> = MutableLiveData()

    val clickTest : MutableLiveData<Once<Unit>> = MutableLiveData()
    val clickAddClass : MutableLiveData<Once<Unit>> = MutableLiveData()
    val clickCompleted : MutableLiveData<Once<Unit>> = MutableLiveData()
    val clickClassList : MutableLiveData<Once<Unit>> = MutableLiveData()
    val clickGiveCollection : MutableLiveData<Once<Unit>> = MutableLiveData()
    val errorInvoked : MutableLiveData<Once<Throwable>> = MutableLiveData()

    /**
     * 숙제 내주기 위한 ViewModel
     */

    var mainChapter : String = ""
    var numChapters : Int = 0


    /**
     * 학생 관리 Class 정보
     */
    val classes : MutableLiveData<List<Class>> = MutableLiveData()

    init {

        curUser.value = authRepository.curUser.value
        refreshAll()

        if ( curUser.value?.type == "teacher") {
            listClasses()
        }

    }

    private fun getWorkPaperList() {
        workPaperRepository.listWorkPapers()
            .addSchedulers()
            .subscribe({
                papers.value = it
                loadPaperCompleted.value = true
                debugE(TAG,it.map{it.id})
            },{
                errorInvoked.value = Once(it)
            }).disposedBy(compositeDisposable)
    }

    private fun getHomeworkList () {

        homeworkRepository.listHomeworkList(curUser.value?.id ?: 0)
            .addSchedulers()
            .subscribe({
                homeworks.value = it
                loadHomeworkCompleted.value = true
            },{
                errorInvoked.value = Once(it)
            }).disposedBy(compositeDisposable)
    }

    private fun getExamList() {

        examRepository.listExam(curUser.value?.id ?: 0)
            .addSchedulers()
            .subscribe({
                exams.value = it
                loadExamCompleted.value = true
                refreshCompleted.value = Once(Unit)
            },{
                errorInvoked.value = Once(it)
            }).disposedBy(compositeDisposable)
    }

    fun refreshAll () {
        getWorkPaperList()
        getExamList()
        getHomeworkList()
    }


    fun refreshAction() {
        when ( curType.value ) {
            "문제지" -> getWorkPaperList()
            "숙제" -> getHomeworkList()
            "시험" -> getExamList()
            else -> {

            }
        }
    }


    fun onClickStartTest() {
        clickTest.value = Once(Unit)
    }

    fun onClickCompleted() {
        clickCompleted.value = Once(Unit)
    }
    fun onClickClassList() {
        clickClassList.value = Once(Unit)
    }

    fun navigatePage( position : Int) {
        currentPage.value = position
    }

    fun onClickGiveCollection() {

        clickGiveCollection.value = Once(Unit)
    }




    fun listClasses () {

        classRepository.listClasses(authRepository.curUser.value?.teacher?.id ?: 0)
            .addSchedulers()
            .subscribe({
                classes.value = it
                debugE(TAG, "Class 호출 성공")
            },{
                errorInvoked.value = Once(it)
            }).disposedBy(compositeDisposable)
    }

    fun onClickAddClass(){
        clickAddClass.value = Once(Unit)
    }


}