package com.math.firstMaker.views.auth

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.math.firstMaker.common.Once
import com.math.firstMaker.common.addSchedulers
import com.math.firstMaker.common.disposedBy
import com.math.firstMaker.repository.AuthRepository
import io.reactivex.disposables.CompositeDisposable


/**
 * created By DORO 2/13/21
 */

class PasswordResetViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val TAG = PasswordResetViewModel::class.java.simpleName

    val compositeDisposable = CompositeDisposable()

    val email : MutableLiveData<String> = MutableLiveData()
    val resetPassword : MutableLiveData<String> = MutableLiveData()
    val password : MutableLiveData<String> = MutableLiveData()
    val passwordConfirm : MutableLiveData<String> = MutableLiveData()

    val notSamePassword : MutableLiveData<Once<Unit>> = MutableLiveData()
    val clickChangePassword : MutableLiveData<Once<Unit>> = MutableLiveData()
    val errorInvoked : MutableLiveData<Once<Throwable>> = MutableLiveData()


    fun onClickChangePassword() {

        if ( password.value != passwordConfirm.value ||( password.value?.length ?: 0 ) <= 6 ) notSamePassword.value = Once(Unit)
        else {

            authRepository.resetPassword(
                email.value,
                resetPassword.value,
                password.value
            )
                .addSchedulers()
                .subscribe({
                    clickChangePassword.value = Once(Unit)
                },{
                    errorInvoked.value = Once(it)
                }).disposedBy(compositeDisposable)
        }
    }



}