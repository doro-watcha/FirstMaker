package com.math.firstMaker.repositoryImpl

import com.math.firstMaker.api.filterValueNotNull
import com.math.firstMaker.api.unWrapCompletable
import com.math.firstMaker.api.unWrapData
import com.math.firstMaker.api.WorkBookAPI
import com.math.firstMaker.model.BigChapter
import com.math.firstMaker.model.WorkBook
import com.math.firstMaker.repository.WorkBookRepository
import io.reactivex.Completable
import io.reactivex.Single


/**
 * created By DORO 2020-02-28
 */

class WorkBookRepositoryImpl(private val api : WorkBookAPI) : WorkBookRepository{

    override fun getWorkBook(workBookId: Int): Single<WorkBook> {
        return api.getWorkBook(workBookId).unWrapData().map{it.workBook}
    }

    override fun listWorkBook(studentId: Int?): Single<List<WorkBook>> {
        val params = hashMapOf(
            "studentId" to studentId
        ).filterValueNotNull()

        return api.listWorkBooks(params).unWrapData().map { it.workBookList }
    }

    override fun listMyWorkBooks(studentId: Int): Single<List<WorkBook>> {
        return api.listMyWorkBooks(studentId).unWrapData().map { it.workBookList}
    }

    override fun listMyChapterList(studentId: Int, workBookId: Int): Single<List<BigChapter>> {

        val params = hashMapOf(
            "workBookId" to workBookId
        ).filterValueNotNull()

        return api.listMyChapterList(studentId, params).unWrapData().map { it.bigChapters}
    }

    override fun buyBigChapter(studentId: Int, workBookId: Int, bigChapterId: Int): Completable {
        val params = hashMapOf(
            "studentId" to studentId,
            "workBookId" to workBookId,
            "bigChapterId" to bigChapterId
        ).filterValueNotNull()

        return api.buyBigChapter(params).unWrapCompletable()
    }
}