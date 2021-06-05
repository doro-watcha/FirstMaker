package com.math.firstMaker.repository

import androidx.annotation.IntRange
import com.math.firstMaker.api.ExamResponse
import com.math.firstMaker.model.Exam
import com.math.firstMaker.model.Homework
import io.reactivex.Completable
import io.reactivex.Single

interface ExamRepository {

    fun getExam (
        examId : Int
    ) : Single<Exam>

    fun createExam(
        title : String,
        problemIdList : List<Int>,
        numChapters : Int,
        mainChapter : String,
        userIdList : List<Int>,
        timeLimit : Int
    ) : Completable


    fun listExam (
        userId : Int
    ) : Single<List<Exam>>

}