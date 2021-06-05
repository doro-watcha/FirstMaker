package com.math.firstMaker.repositoryImpl

import com.math.firstMaker.api.filterValueNotNull
import com.math.firstMaker.api.unWrapCompletable
import com.math.firstMaker.api.unWrapData
import com.math.firstMaker.api.*
import com.math.firstMaker.repository.ProblemRepository
import com.math.firstMaker.model.Problem
import com.math.firstMaker.model.Source
import io.reactivex.Completable
import io.reactivex.Single


class ProblemRepositoryImpl (private val api : ProblemAPI): ProblemRepository {

    override fun getProblem(problemId: Int): Single<Problem> {
        return api.getProblem(problemId).unWrapData().map{it.problem}
    }

    override fun findProblems(
        smallChapterIdList: List<Int>,
        numberOfProblems: List<Int>,
        isNotDuplicated : Boolean,
        minLevel: Int,
        maxLevel: Int
    ): Single<ProblemListResponse> {

        val params = hashMapOf(
            "smallChapterIdList" to smallChapterIdList,
            "numberOfProblems" to numberOfProblems,
            "isNotDuplicated" to isNotDuplicated,
            "minLevel" to minLevel,
            "maxLevel" to maxLevel
        ).filterValueNotNull()

        return api.listProblems(params).unWrapData()
    }

    override fun registerBlackList(problemId: Int, teacherId : Int ): Completable {

        val params = hashMapOf(
            "problemId" to problemId,
            "teacherId" to teacherId
        ).filterValueNotNull()
        return api.registerBlackList( params ).unWrapCompletable()
    }

    override fun listReplaceProblem(
        smallChapterId: Int,
        numProblem: Int,
        filterList: List<Int>,
        minLevel : Int,
        maxLevel : Int
    ): Single<List<Problem>> {

        val params = hashMapOf(
            "smallChapterId" to smallChapterId,
            "numProblem" to numProblem,
            "filterList" to filterList,
            "minLevel" to minLevel,
            "maxLevel" to maxLevel
        ).filterValueNotNull()

        return api.listReplaceProblem(params).unWrapData().map { it.problems}
    }

    override fun listSources(): Single<List<Source>> {

        return api.listSources().unWrapData().map{it.sources}
    }


}

