package com.math.firstMaker.views.home.classList.detail

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.math.firstMaker.DateParserUtil
import com.math.firstMaker.common.Once
import com.math.firstMaker.common.addSchedulers
import com.math.firstMaker.common.disposedBy
import com.math.firstMaker.debugE
import com.math.firstMaker.model.*
import com.math.firstMaker.repository.ClassRepository
import com.math.firstMaker.repository.ExamRepository
import com.math.firstMaker.repository.HomeworkRepository
import com.math.firstMaker.repository.UserRepository
import io.reactivex.disposables.CompositeDisposable


/**
 * created By DORO 1/3/21
 */

class ClassDetailViewModel (
    val classId : Int,
    val userRepository: UserRepository,
    val classRepository: ClassRepository,
    val homeworkRepository: HomeworkRepository,
    val examRepository: ExamRepository,
    val dateParserUtil: DateParserUtil
) : ViewModel() {

    private val TAG = ClassDetailViewModel::class.java.simpleName

    private val compositeDisposable = CompositeDisposable()

    val curClass : MutableLiveData<Class> = MutableLiveData()

    val students : MutableLiveData<List<Student>> = MutableLiveData()

    val studentName : MutableLiveData<String> = MutableLiveData()
    val searchStudents : MutableLiveData<List<User>> = MutableLiveData()
    val curStudent : MutableLiveData<User> = MutableLiveData()


    val pickStudent : MutableLiveData<Student> = MutableLiveData()

    val navigatePage : MutableLiveData<Int> = MutableLiveData(0)
    val deleteStudentCompleted : MutableLiveData<Once<Unit>> = MutableLiveData()
    val addStudentCompleted : MutableLiveData<Once<Unit>> = MutableLiveData()
    val errorInvoked : MutableLiveData<Once<Throwable>> = MutableLiveData()

    /**
     * 학생들의 숙제, 시험
     */

    val homeworks : MutableLiveData<List<Homework>> = MutableLiveData()
    val exams : MutableLiveData<List<Exam>> = MutableLiveData()

    val onHomeworkLoadCompleted : MutableLiveData<Boolean> = MutableLiveData()
    val onExamLoadCompleted : MutableLiveData<Boolean> = MutableLiveData()

    val onCollectionLoadCompleted = MediatorLiveData<Boolean>().apply {

        this.addSource(onHomeworkLoadCompleted){
            this.value = it && onExamLoadCompleted.value ?: false
        }
        this.addSource(onExamLoadCompleted){
            this.value = it && onExamLoadCompleted.value ?: false
        }
    }




    /**
     * 학생들의 결과를 보기 위한 곧
     */

    val curTitle : MutableLiveData<String> = MutableLiveData()
    val status : MutableLiveData<String> = MutableLiveData()
    val correctRate : MutableLiveData<String> = MutableLiveData()
    val spendingTime : MutableLiveData<Int> = MutableLiveData()
    val results : MutableLiveData<List<Note>> = MutableLiveData()
    var curId = 0
    var curType = ""

    val getFullTime = MediatorLiveData<String>().apply {

        this.addSource(spendingTime){
            this.value = dateParserUtil.calculateTimeBySecond(it ?: 0)
        }
    }
    init {

        getClass()

    }

    fun getClass() {

        classRepository.getClass(classId)
            .addSchedulers()
            .subscribe({
                curClass.value = it
                students.value = it.classBelongs?.map { it.student}
            },{
                errorInvoked.value = Once(it)
            }).disposedBy(compositeDisposable)
    }


    fun listUsersByStudentName( query : String ) {

        userRepository.listUsersByStudentName(query)
            .addSchedulers()
            .subscribe({
                searchStudents.value = it
            },{
                errorInvoked.value = Once(it)
            }).disposedBy(compositeDisposable)
    }

    fun addStudent () {

        debugE(TAG, curStudent.value?.student?.id)

        classRepository.addStudent(curStudent.value?.student?.id ?: 0 ,classId)
            .addSchedulers()
            .subscribe({
                addStudentCompleted.value = Once(Unit)
            },{
                errorInvoked.value = Once(it)
            }).disposedBy(compositeDisposable)
    }

    fun deleteStudent ( studentId : Int ) {

        classRepository.deleteStudent(studentId, classId)
            .addSchedulers()
            .subscribe({
                deleteStudentCompleted.value = Once(Unit)
            },{
                errorInvoked.value = Once(it)
            }).disposedBy(compositeDisposable)
    }

    fun listStudentCollection () {

        homeworkRepository.listHomeworkList(pickStudent.value?.id ?: 0)
            .addSchedulers()
            .subscribe({
                homeworks.value = it
                onExamLoadCompleted.value = true
            },{
                errorInvoked.value = Once(it)
            }).disposedBy(compositeDisposable)

        examRepository.listExam(pickStudent.value?.id ?: 0)
            .addSchedulers()
            .subscribe({
                exams.value = it
                onExamLoadCompleted.value = true
            },{
                errorInvoked.value = Once(it)
            }).disposedBy(compositeDisposable)

    }

    fun navigatePage ( position : Int) {
        navigatePage.value = position
    }

    override fun onCleared() {
        super.onCleared()

        compositeDisposable.clear()
    }


}