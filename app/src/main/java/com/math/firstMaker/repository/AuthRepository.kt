package com.math.firstMaker.repository

import androidx.lifecycle.LiveData
import com.math.firstMaker.api.LoginResponse
import com.math.firstMaker.api.SignUpResponse
import com.math.firstMaker.model.User
import io.reactivex.Completable
import io.reactivex.Single

interface AuthRepository {

    fun login (
        username : String,
        password : String
    ) : Single<LoginResponse>

    /**
     * Current User Instance(me)
     */
    val curUser: LiveData<User>

    fun setCurrentUser(user: User)

    /**
     * Sign Out
     *
     * @return sign out is success
     */
    fun signOut(): Boolean

    fun signup (
        email : String,
        password : String,
        name : String,
        school : String?,
        mathGrade : String?,
        grade : String?,
        type : String,
        teacherCode : String?
    ) : Completable

    fun resetPassword(
        email : String?,
        resetPassword : String?,
        password :String?
    ) : Completable
}