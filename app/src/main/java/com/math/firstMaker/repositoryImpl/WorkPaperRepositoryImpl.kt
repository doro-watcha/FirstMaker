package com.math.firstMaker.repositoryImpl

import com.math.firstMaker.api.filterValueNotNull
import com.math.firstMaker.api.unWrapCompletable
import com.math.firstMaker.api.unWrapData
import com.math.firstMaker.api.WorkPaperAPI
import com.math.firstMaker.model.WorkPaper
import com.math.firstMaker.repository.WorkPaperRepository
import io.reactivex.Completable
import io.reactivex.Single

class WorkPaperRepositoryImpl (private val api : WorkPaperAPI) : WorkPaperRepository {
    override fun createWorkPaper(title: String, problemIds: List<Int> , numChapters : Int, mainChapter : String): Completable {
        val params = hashMapOf(
            "title" to title,
            "problemIdList" to problemIds,
            "numChapters" to numChapters,
            "mainChapter" to mainChapter
        ).filterValueNotNull()
        return api.createWorkPaper(params).unWrapCompletable()
    }

    override fun getWorkPaper(workPaperId: Int): Single<WorkPaper> {
        return api.getWorkPaper(workPaperId).unWrapData().map{it.workPaper}
    }


    override fun listWorkPapers(): Single<List<WorkPaper>> {

        return api.listWorkPapers().unWrapData().map { it.workPaperList }
    }
}
