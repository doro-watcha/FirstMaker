package com.math.firstMaker.views.auth.signUp

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.math.firstMaker.common.Once
import com.math.firstMaker.common.addSchedulers
import com.math.firstMaker.common.disposedBy
import com.math.firstMaker.debugE
import com.math.firstMaker.repository.AuthRepository
import io.reactivex.disposables.CompositeDisposable


/**
 * created By DORO 2020-03-21
 */

class SignUpViewModel (
    private val authRepository: AuthRepository
) : ViewModel() {

    private val TAG = SignUpViewModel::class.java.simpleName

    private val compositeDisposable = CompositeDisposable()

    val userEmail : MutableLiveData<String> = MutableLiveData()
    val password : MutableLiveData<String> = MutableLiveData()
    val school : MutableLiveData<String> = MutableLiveData()
    val admissionYear : MutableLiveData<String> = MutableLiveData()
    val mathGrade : MutableLiveData<String> = MutableLiveData()
    val name : MutableLiveData<String> = MutableLiveData()
    val resetPassword : MutableLiveData<String> = MutableLiveData()

    val teacherCode : MutableLiveData<String> = MutableLiveData()

    val navigatePage : MutableLiveData<Int> = MutableLiveData()
    val clickFinish : MutableLiveData<Once<Unit>> = MutableLiveData()
    val signUpSuccess : MutableLiveData<Once<Unit>> = MutableLiveData()
    val errorInvoked : MutableLiveData<Once<Throwable>> = MutableLiveData()


    init {



    }


    fun signUp() {


        authRepository.signup(
            userEmail.value ?: " ",
            password.value ?: " ",
            name.value ?: "",
            school.value,
            mathGrade.value,
            admissionYear.value,
            "student",
            null
        )
            .addSchedulers()
            .subscribe({
                debugE(TAG, "회원가입성공!")
                signUpSuccess.value = Once(Unit)
            },{
                debugE(TAG, it.message)
                errorInvoked.value = Once(it)
            }).disposedBy(compositeDisposable)
    }

    fun teacherSignUp () {

        authRepository.signup(
            userEmail.value ?: "",
            password.value ?: "",
            name.value ?: "",
            null,
            null,
            null,
            "teacher",
            teacherCode.value ?: ""
        )
            .addSchedulers()
            .subscribe({

                signUpSuccess.value = Once(Unit)
            },{
                errorInvoked.value = Once(it)
            }).disposedBy(compositeDisposable)
    }

    fun onClickFinish() {
        clickFinish.value = Once(Unit)
    }

    fun changeFragment ( position : Int) {
        navigatePage.value = position
    }




}