package com.math.firstMaker.views.auth

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.math.firstMaker.common.Once
import com.math.firstMaker.common.addSchedulers
import com.math.firstMaker.common.disposedBy
import com.math.firstMaker.common.with
import com.math.firstMaker.repository.AuthRepository
import com.math.firstMaker.AppPreference
import com.math.firstMaker.debugE
import io.reactivex.disposables.CompositeDisposable
import net.bytebuddy.implementation.bytecode.Throw


/**
 * created By DORO 2020-03-21
 */

class LoginViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val TAG = LoginViewModel::class.java.simpleName

    val compositeDisposable = CompositeDisposable()

    val userEmail: MutableLiveData<String> = MutableLiveData()
    val password: MutableLiveData<String> = MutableLiveData()

    val loginSuccess : MutableLiveData<Once<Unit>>  = MutableLiveData()
    val clickResetPassword : MutableLiveData<Once<Unit>> = MutableLiveData()
    val errorInvoked : MutableLiveData<Once<Throwable>> = MutableLiveData()
    val goSignUp : MutableLiveData<Once<Unit>> = MutableLiveData()


    fun login() {
        authRepository.login(userEmail.value ?: "", password.value ?: "")
            .addSchedulers()
            .subscribe(
                // Success Handler
                {
                    debugE(TAG, "일단 성공하긴했어!" )
                    authRepository.setCurrentUser(it.user)
                    loginSuccess.value = Once(Unit)
                }, {
                    errorInvoked.value = Once(it)
                }).disposedBy(compositeDisposable)


    }
    fun signUp(){
        goSignUp.value = Once(Unit)
    }

    fun onClickResetPassword() {
        clickResetPassword.value = Once(Unit)
    }
}