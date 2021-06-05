package com.math.firstMaker.views.auth

import android.annotation.SuppressLint
import android.widget.Toast
import com.math.firstMaker.MainApplication.Companion.context
import com.math.firstMaker.base.presenter.BasePresenter
import com.math.firstMaker.common.addSchedulers
import com.math.firstMaker.common.with
import com.math.firstMaker.repository.AuthRepository
import com.math.firstMaker.repository.UserRepository
import com.math.firstMaker.AppPreference
import com.math.firstMaker.debugE

class AuthPresenter(private val userRepository: UserRepository, private val authRepository: AuthRepository ) : BasePresenter<IAuthContract.View>(), IAuthContract.Presenter {
    private val TAG: String = "AuthPresenter"

    @SuppressLint("CheckResult")
    override fun signup( username : String, password : String, password2 : String, name : String, school : String, admissionYear : String, mathGrade : String) {

    }

    @SuppressLint("CheckResult")
    override fun login(userid: String, password: String) {
        authRepository.login(userid, password).with()
            .addSchedulers()
            .subscribe(
                // Success Handler
                {

                    view?.onAuthCompleted(it.token)

                },
                // Error Handler
                {
                    view?.onAuthFailed(it.message!!)
                }
            ).apply {
                addToDisposables(this)
            }
    }

}
