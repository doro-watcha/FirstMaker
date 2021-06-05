package com.math.firstMaker.repository

import androidx.annotation.IntRange
import com.math.firstMaker.api.ProblemListResponse
import com.math.firstMaker.api.ProblemResponse
import com.math.firstMaker.model.Problem
import com.math.firstMaker.model.Source
import io.reactivex.Completable
import io.reactivex.Single

interface ProblemRepository {

    fun getProblem(
        problemId: Int
    ): Single<Problem>

    fun findProblems(
        smallChapterIdList : List<Int>,
        numberOfProblems : List<Int>,
        isNotDuplicated : Boolean,
        minLevel: Int,
        maxLevel : Int

    ): Single<ProblemListResponse>

    fun registerBlackList(
        problemId: Int,
        teacherId : Int
    ): Completable

    fun listReplaceProblem(
        smallChapterId : Int ,
        numProblem : Int ,
        filterList : List<Int>,
        minLevel : Int,
        maxLevel : Int
    ) : Single<List<Problem>>

    fun listSources() : Single<List<Source>>
}