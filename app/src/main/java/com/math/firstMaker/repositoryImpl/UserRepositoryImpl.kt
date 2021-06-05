package com.math.firstMaker.repositoryImpl


import com.math.firstMaker.api.unWrapData
import com.math.firstMaker.api.UserAPI
import com.math.firstMaker.model.User
import com.math.firstMaker.repository.UserRepository
import io.reactivex.Single


class UserRepositoryImpl(private val api: UserAPI) : UserRepository {
    override fun listUsersByStudentName(studentName: String): Single<List<User>> {
        return api.listUsersByStudentName(studentName).unWrapData().map{it.students}
    }

}