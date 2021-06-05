package com.math.firstMaker.repositoryImpl

import com.math.firstMaker.api.ClassAPI
import com.math.firstMaker.api.filterValueNotNull
import com.math.firstMaker.api.unWrapCompletable
import com.math.firstMaker.api.unWrapData
import com.math.firstMaker.model.Class
import com.math.firstMaker.repository.ClassRepository
import io.reactivex.Completable
import io.reactivex.Single


/**
 * created By DORO 1/3/21
 */

class ClassRepositoryImpl ( val api : ClassAPI ) :ClassRepository {

    override fun getClass(classId: Int): Single<Class> {
        return api.getClass(classId).unWrapData().map { it.classInfo}
    }

    override fun createClass(name: String, teacherId: Int, studentIdList : List<Int>?): Completable {
        val params = hashMapOf(
            "name" to name,
            "teacherId" to teacherId,
            "studentIdList" to studentIdList
        ).filterValueNotNull()

        return api.createClass(params).unWrapCompletable()
    }

    override fun listClasses(teacherId: Int): Single<List<Class>> {

        val params = hashMapOf(
            "teacherId" to teacherId
        ).filterValueNotNull()
        return api.listClasses(params).unWrapData().map{it.classes}
    }

    override fun addStudent(studentId: Int, classId: Int): Completable {

        val params = hashMapOf(
            "studentId" to studentId,
            "classId" to classId
        ).filterValueNotNull()

        return api.addStudent(params).unWrapCompletable()
    }

    override fun deleteStudent(studentId: Int, classId: Int): Completable {

        val params = hashMapOf(
            "studentId" to studentId,
            "classId" to classId
        ).filterValueNotNull()

        return api.deleteStudent(params).unWrapCompletable()
    }

    override fun listClassesByStudentId(studentId: Int): Single<List<Class>> {
        return api.listClassesByStudentId(studentId).unWrapData().map { it.classes}
    }

}