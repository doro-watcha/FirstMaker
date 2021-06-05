package com.math.firstMaker.repositoryImpl

import com.math.firstMaker.api.filterValueNotNull
import com.math.firstMaker.api.unWrapCompletable
import com.math.firstMaker.api.unWrapData
import com.math.firstMaker.api.ExamAPI
import com.math.firstMaker.repository.ExamRepository
import com.math.firstMaker.model.Exam
import io.reactivex.Completable
import io.reactivex.Single

class ExamRepositoryImpl(private val api: ExamAPI) : ExamRepository {


    override fun getExam(examId: Int) : Single<Exam>{
        return api.getExam(examId).unWrapData().map{it.exam}
    }

    override fun createExam(
        title: String,
        problemIdList: List<Int>,
        numChapters: Int,
        mainChapter: String,
        userIdList: List<Int>,
        timeLimit: Int
    ): Completable {

        val params = hashMapOf(
            "title" to title,
            "problemIdList" to problemIdList,
            "numChapters" to numChapters,
            "mainChapter" to mainChapter,
            "userIdList" to userIdList,
            "timeLimit" to timeLimit
        ).filterValueNotNull()

        return api.createExam(params).unWrapCompletable()
    }

    override fun listExam(userId: Int): Single<List<Exam>> {

        val params = hashMapOf(
            "userId" to userId
        ).filterValueNotNull()

        return api.listExams(params).unWrapData().map{it.examList}
    }




}