package com.math.firstMaker.repositoryImpl

import com.math.firstMaker.api.StudentAPI
import com.math.firstMaker.model.Student
import com.math.firstMaker.repository.StudentRepository
import io.reactivex.Completable
import io.reactivex.Single

class StudentRepositoryImpl (private val api : StudentAPI) : StudentRepository {
    override fun addStudent(examId: Int): Completable {
        TODO("Not yet implemented")
    }

    override fun listStudents(teacherId: Int): Single<List<Student>> {
        TODO("Not yet implemented")
    }
}