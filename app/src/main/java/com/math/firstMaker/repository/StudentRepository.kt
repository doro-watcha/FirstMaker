package com.math.firstMaker.repository

import androidx.annotation.IntRange
import com.math.firstMaker.model.Student
import io.reactivex.Completable
import io.reactivex.Single

interface StudentRepository {

    fun addStudent(
        examId : Int
    ) : Completable

    fun listStudents(
        teacherId : Int
    ) : Single<List<Student>>
}