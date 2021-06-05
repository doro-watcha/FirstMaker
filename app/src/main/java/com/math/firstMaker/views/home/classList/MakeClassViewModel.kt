package com.math.firstMaker.views.home.classList

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.math.firstMaker.broadcast.Broadcast
import com.math.firstMaker.common.Once
import com.math.firstMaker.common.addSchedulers
import com.math.firstMaker.common.disposedBy
import com.math.firstMaker.model.User
import com.math.firstMaker.repository.AuthRepository
import com.math.firstMaker.repository.ClassRepository
import com.math.firstMaker.repository.UserRepository
import io.reactivex.disposables.CompositeDisposable


/**
 * created By DORO 2/13/21
 */
class MakeClassViewModel(
    private val userRepository: UserRepository,
    private val classRepository: ClassRepository,
    private val authRepository: AuthRepository
) : ViewModel() {


    val searchStudents : MutableLiveData<List<User>> = MutableLiveData(listOf())
    val students : MutableLiveData<List<User>> = MutableLiveData(listOf())
    val className : MutableLiveData<String> = MutableLiveData()
    val studentName : MutableLiveData<String> = MutableLiveData()

    val needClassName : MutableLiveData<Once<Unit>> = MutableLiveData()
    val classCreateCompleted : MutableLiveData<Once<Unit>> = MutableLiveData()
    val errorInvoked : MutableLiveData<Once<Throwable>> = MutableLiveData()
    val compositeDisposable = CompositeDisposable()



    fun listUsersByStudentName( query : String ) {

        userRepository.listUsersByStudentName(query)
            .addSchedulers()
            .subscribe({
                searchStudents.value = it
            },{
                errorInvoked.value = Once(it)
            }).disposedBy(compositeDisposable)
    }

    fun onClickMakeClass () {

        if ( className.value == null || className.value?.length == 0) {
            needClassName.value = Once(Unit)
        }
        else {

            val studentIdList = students.value?.map { it.student }?.map { it?.id ?: 0}
            classRepository.createClass(
                name = className.value ?: "",
                teacherId = authRepository.curUser.value?.teacher?.id ?: 0,
                studentIdList = studentIdList
            )
                .addSchedulers()
                .subscribe({
                    Broadcast.classCreateBroadcast.onNext(Unit)
                    classCreateCompleted.value = Once(Unit)
                },{
                    errorInvoked.value = Once(it)
                }).disposedBy(compositeDisposable)
        }

    }
}