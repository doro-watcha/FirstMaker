package com.math.firstMaker.repositoryImpl

import android.os.Looper
import androidx.annotation.MainThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.math.firstMaker.AppPreference
import com.math.firstMaker.api.unWrapData
import com.math.firstMaker.api.AuthAPI
import com.math.firstMaker.api.LoginResponse
import com.math.firstMaker.api.filterValueNotNull
import com.math.firstMaker.api.unWrapCompletable
import com.math.firstMaker.debugE
import com.math.firstMaker.model.User
import com.math.firstMaker.repository.AuthRepository
import com.math.firstMaker.utils.TokenUtil
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers

class AuthRepositoryImpl (
    private val api : AuthAPI,
    private val appPreference: AppPreference,
    private val gson : Gson,
    private val tokenUtil : TokenUtil
) : AuthRepository {

    private val TAG = AuthRepositoryImpl::class.java.simpleName

    override fun login(username: String, password: String): Single<LoginResponse> {
        return api.login(username,password).unWrapData().observeOn(AndroidSchedulers.mainThread())
            .doOnSuccess{
                tokenUtil.saveToken(it.token)
                setCurrentUser(it.user)
            }
    }

    private val _curUser: MutableLiveData<User> = MutableLiveData()
    override val curUser: LiveData<User> = _curUser

    init {
        try {
            appPreference.curUser?.run {
                gson.fromJson(this, User::class.java)?.run {
                    _curUser.value = this
                }
            }
        } catch (t: Throwable) {
            debugE(TAG, "SP 에서 유저 불러오기 실패 $t")
        }
    }

    override fun setCurrentUser(user: User) {
        appPreference.curUser = gson.toJson(user)
        if (Looper.getMainLooper() == Looper.myLooper())
            this._curUser.value = user
        else
            this._curUser.postValue(user)
    }


    @MainThread
    override fun signOut(): Boolean {
        val result = curUser.value != null

        tokenUtil.clearToken()

        if (Looper.getMainLooper() == Looper.myLooper())
            _curUser.value = null
        else
            _curUser.postValue(null)

        appPreference.curUser = null
        return result
    }

    override fun signup(
        email: String,
        password: String,
        name : String,
        school: String?,
        mathGrade: String?,
        grade: String?,
        type : String,
        teacherCode : String?
    ): Completable {
        val params = hashMapOf(
            "email" to email,
            "password" to password,
            "name" to name,
            "school" to school,
            "mathGrade" to mathGrade,
            "grade" to grade,
            "type" to type,
            "teacherCode" to teacherCode
        ).filterValueNotNull()

        return api.signup(params).unWrapCompletable()
    }

    override fun resetPassword(
        email: String?,
        resetPassword: String?,
        password: String?
    ): Completable {

        val params = hashMapOf(
            "email" to email,
            "resetPassword" to resetPassword,
            "newPassword" to password
        ).filterValueNotNull()

        return api.resetPassword(params).unWrapCompletable()
    }



}