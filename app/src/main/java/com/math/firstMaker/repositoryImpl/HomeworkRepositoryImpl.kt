package com.math.firstMaker.repositoryImpl

import com.math.firstMaker.api.HomeworkAPI
import com.math.firstMaker.api.filterValueNotNull
import com.math.firstMaker.api.unWrapCompletable
import com.math.firstMaker.api.unWrapData
import com.math.firstMaker.model.Homework
import com.math.firstMaker.repository.HomeworkRepository
import io.reactivex.Completable
import io.reactivex.Single


/**
 * created By DORO 1/4/21
 */

class HomeworkRepositoryImpl (val api : HomeworkAPI ) : HomeworkRepository {


    override fun getHomework(homeworkId: Int): Single<Homework> {
        return api.getHomework(homeworkId).unWrapData().map {it.homework}
    }

    override fun createHomework(
        title: String,
        problemIds: List<Int>,
        numChapters: Int,
        mainChapter: String,
        userIdList: List<Int>
    ): Completable {

        val params = hashMapOf(
            "title" to title,
            "problemIdList" to problemIds,
            "numChapters" to numChapters,
            "mainChapter" to mainChapter,
            "userIdList" to userIdList
        ).filterValueNotNull()

        return api.createHomework(params).unWrapCompletable()
    }

    override fun listHomeworkList(
        userId : Int
    ): Single<List<Homework>> {
        val params = hashMapOf(
            "userId" to userId
        ).filterValueNotNull()
        return api.listHomeworkList(params).unWrapData().map { it.homeworkList}
    }
}