package com.math.firstMaker.views.setting

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.math.firstMaker.AppPreference
import com.math.firstMaker.DateParserUtil
import com.math.firstMaker.common.Once
import com.math.firstMaker.common.addSchedulers
import com.math.firstMaker.common.disposedBy
import com.math.firstMaker.debugE
import com.math.firstMaker.model.BigChapter
import com.math.firstMaker.model.Class
import com.math.firstMaker.model.Note
import com.math.firstMaker.model.User
import com.math.firstMaker.repository.AuthRepository
import com.math.firstMaker.repository.ClassRepository
import com.math.firstMaker.repository.NoteRepository
import io.reactivex.disposables.CompositeDisposable
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


/**
 * created By DORO 2020/04/02
 */

class SettingViewModel (
    private val authRepository: AuthRepository,
    private val noteRepository: NoteRepository,
    private val dateParserUtil : DateParserUtil,
    private val classRepository: ClassRepository
) : ViewModel() {

    private val TAG = SettingViewModel::class.java.simpleName

    private val compositeDisposable = CompositeDisposable()

    val notes : MutableLiveData<List<Note>> = MutableLiveData()

    val myClasses : MutableLiveData<List<Class>> = MutableLiveData()

    var startDate = ""
    var endDate = ""

    val vulnerableChapter : MutableLiveData<List<BigChapter>> = MutableLiveData()


    private val weeklyRightProblemNumber : MutableLiveData<ArrayList<Int>> = MutableLiveData()



    val weeklyCorrectRate : MutableLiveData<ArrayList<Float>> = MutableLiveData()


    val weeklyProblemNumber = MediatorLiveData<ArrayList<Int>?>().apply {
        this.addSource(notes) {

            if (it.size > 0) {
                it.forEach { note ->

                    /**
                     * Note을 검사해서 맞는 쪽으로 족족 패스
                     */

                    val position = dateParserUtil.calculateDiffWithCurrent(note.updatedAt)
                    this.value?.set(position, this.value?.get(position)?.plus(1) ?: 0)

                    if (note.status == "맞음") weeklyRightProblemNumber.value?.set(
                        position,
                        weeklyRightProblemNumber.value?.get(position)?.plus(1) ?: 0
                    )
                }

                val correctRateList = arrayListOf(0f, 0f, 0f, 0f, 0f, 0f, 0f)
                weeklyRightProblemNumber.value?.forEachIndexed { index, rightProblemNumber ->

                    val wholeProblem = this.value?.get(index) ?: 0


                    if (wholeProblem != 0) {

                        debugE(TAG, "index $index")
                        debugE(TAG, "전체문제 " + wholeProblem)
                        debugE(TAG, "rightProblemNumer " + rightProblemNumber)
                        debugE(TAG, rightProblemNumber / wholeProblem)

                        val correctRate = ((rightProblemNumber) / wholeProblem.toFloat()) * 100

                        debugE(TAG, correctRate)
                        correctRateList.set(
                            index,
                            correctRate
                        )
                    }
                }

                weeklyCorrectRate.value = correctRateList

                debugE(TAG, "whoeProblemNumber = " + this.value)
                debugE(TAG, "weeklyRightProblem = " + weeklyRightProblemNumber.value)
                debugE(TAG, "weeklyCorrectRate = " + weeklyCorrectRate.value)
            }
        }
    }

    val clickLogOut : MutableLiveData<Once<Unit>> = MutableLiveData()

    val curUser : MutableLiveData<User> = MutableLiveData(authRepository.curUser.value!!)

    val errorInvoked : MutableLiveData<Once<Throwable>> = MutableLiveData()

    init {


        initData()
        listNote()

        if ( curUser.value?.type == "student"){

            listMyClasses()
        }

    }

    private fun listNote () {

        initData()

        val startDayFormat = SimpleDateFormat("yyyy-MM-dd 00:00:00")
        val endDayFormat = SimpleDateFormat("yyyy-MM-dd 23:59:59")
        val today = Calendar.getInstance()
        endDate = endDayFormat.format(today.time)

        today.set(Calendar.DAY_OF_WEEK,Calendar.SUNDAY)
        startDate = startDayFormat.format(today.time.time)

        debugE(TAG, startDate)
        debugE(TAG, endDate)
        debugE(TAG, curUser.value?.student?.id )
        noteRepository.listNotes(
            startDate = startDate,
            endDate = endDate
        )
            .addSchedulers()
            .subscribe({

                notes.value = it

                debugE(TAG, "NOTE SIZE " + it.map{it.updatedAt})


            },{
                errorInvoked.value = Once(it)
            }).disposedBy(compositeDisposable)
    }

    fun refresh() {
        listNote()
    }

    private fun listMyClasses() {

        classRepository.listClassesByStudentId(curUser.value?.student?.id ?: 0)
            .addSchedulers()
            .subscribe({
                debugE(TAG, "Class List Completed")
                myClasses.value = it
            },{
               errorInvoked.value = Once(it)
            }).disposedBy(compositeDisposable)
    }

    fun onClickLogOut() {

        authRepository.signOut()

        clickLogOut.value = Once(Unit)
    }


    fun initData () {

        weeklyProblemNumber.value = arrayListOf(0,0,0,0,0,0,0)
        weeklyRightProblemNumber.value = arrayListOf(0,0,0,0,0,0,0)
        weeklyCorrectRate.value = arrayListOf(0f,0f,0f,0f,0f,0f,0f)
    }




}