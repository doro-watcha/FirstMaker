package com.math.firstMaker.repository

import com.math.firstMaker.model.Class
import io.reactivex.Completable
import io.reactivex.Single


/**
 * created By DORO 1/3/21
 */

interface ClassRepository {

    fun getClass (
        classId : Int
    ) : Single<Class>

    fun createClass (
        name : String,
        teacherId : Int,
        studentIdList : List<Int>?
    ) : Completable

    fun listClasses (
        teacherId : Int
    ) : Single<List<Class>>


    fun addStudent (
        studentId : Int,
        classId : Int
    ) : Completable

    fun deleteStudent (
        studentId : Int,
        classId : Int
    ) : Completable

    fun listClassesByStudentId (
        studentId : Int
    ) : Single<List<Class>>
}