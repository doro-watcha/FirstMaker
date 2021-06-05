package com.math.firstMaker.repository

import com.math.firstMaker.api.StudentListResponse
import com.math.firstMaker.api.UserResponse
import com.math.firstMaker.model.User
import io.reactivex.Completable
import io.reactivex.Single

interface UserRepository {

    fun listUsersByStudentName(
        studentName : String
    ) : Single<List<User>>

}