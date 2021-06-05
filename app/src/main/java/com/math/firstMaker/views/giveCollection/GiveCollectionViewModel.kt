package com.math.firstMaker.views.giveCollection

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.math.firstMaker.common.Once
import com.math.firstMaker.common.addSchedulers
import com.math.firstMaker.common.disposedBy
import com.math.firstMaker.model.ShareRequest
import com.math.firstMaker.model.User
import com.math.firstMaker.repository.AuthRepository
import com.math.firstMaker.repository.ExamRepository
import com.math.firstMaker.repository.HomeworkRepository
import com.math.firstMaker.repository.UserRepository
import io.reactivex.disposables.CompositeDisposable


/**
 * created By DORO 2/13/21
 */

class GiveCollectionViewModel (
    val shareRequest: ShareRequest,
    private val examRepository: ExamRepository,
    private val homeworkRepository: HomeworkRepository,
    private val userRepository: UserRepository,
    private val authRepository: AuthRepository

): ViewModel() {

    val curUser : MutableLiveData<User?> = MutableLiveData(authRepository.curUser.value)

    val studentName: MutableLiveData<String> = MutableLiveData()
    val searchStudents: MutableLiveData<List<User>> = MutableLiveData(listOf())
    val students: MutableLiveData<List<User>> = MutableLiveData(listOf())

    val timeLimit : MutableLiveData<String> = MutableLiveData()

    var isNew : Boolean = false



    val onSaveCollection : MutableLiveData<Once<String>> = MutableLiveData()
    val noTimeLimit : MutableLiveData<Once<Unit>> = MutableLiveData()
    val noStudent : MutableLiveData<Once<Unit>> = MutableLiveData()
    val errorInvoked : MutableLiveData<Once<Throwable>> = MutableLiveData()
    val clickConfirm : MutableLiveData<Once<Unit>> = MutableLiveData()
    val compositeDisposable = CompositeDisposable()

    fun onClickGiveCollection(type: String) {

        if (students.value?.size ?: 0 > 0) {
            val userIdList = if ( isNew ) students.value?.map{it.id}!!
            else students.value?.map { it.id }!! + listOf(curUser.value?.id ?: 0)

            if (type == "시험") {

                if ( (timeLimit.value?.length ?: 0 ) <= 0) noTimeLimit.value = Once(Unit)
                else {
                    examRepository.createExam(
                        shareRequest.title,
                        shareRequest.problems.map { it.id },
                        shareRequest.numChapters,
                        shareRequest.mainChapter,
                        userIdList,
                        Integer.parseInt(timeLimit.value ?: 0.toString())
                    )
                        .addSchedulers()
                        .subscribe({

                            onSaveCollection.value = Once("시험")
                        }, {
                            errorInvoked.value = Once(it)
                        }).disposedBy(compositeDisposable)
                }

            } else if (type == "숙제") {

                homeworkRepository.createHomework(
                    shareRequest.title,
                    shareRequest.problems.map { it.id },
                    shareRequest.numChapters,
                    shareRequest.mainChapter,
                    userIdList
                )
                    .addSchedulers()
                    .subscribe({
                        onSaveCollection.value = Once("숙제")
                    },{
                        errorInvoked.value = Once(it)
                    }).disposedBy(compositeDisposable)
            }
        } else {
            noStudent.value = Once(Unit)
        }
    }


    fun listUsersByStudentName(query: String) {

        userRepository.listUsersByStudentName(query)
            .addSchedulers()
            .subscribe({
                searchStudents.value = it
            }, {
                errorInvoked.value = Once(it)
            }).disposedBy(compositeDisposable)
    }

    fun onClickConfirm() {
        clickConfirm.value = Once(Unit)
    }


}