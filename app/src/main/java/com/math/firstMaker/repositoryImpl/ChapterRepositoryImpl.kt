package com.math.firstMaker.repositoryImpl

import com.math.firstMaker.api.ChapterAPI
import com.math.firstMaker.api.filterValueNotNull
import com.math.firstMaker.api.unWrapData
import com.math.firstMaker.model.BigChapter
import com.math.firstMaker.model.MiddleChapter
import com.math.firstMaker.model.SmallChapter
import com.math.firstMaker.model.Subject
import com.math.firstMaker.repository.ChapterRepository
import io.reactivex.Single


/**
 * created By DORO 2020/11/01
 */

class ChapterRepositoryImpl (
    val api : ChapterAPI
): ChapterRepository {
    override fun getSubjectList(): Single<List<Subject>> {

        val params = hashMapOf(
            "fuck" to 0
        ).filterValueNotNull()
        return api.listSubjects(params).unWrapData().map{it.subjects}
    }

    override fun getBigChapterList(subjectId: Int): Single<List<BigChapter>> {
        val params = hashMapOf(
            "subjectId" to subjectId
        ).filterValueNotNull()

        return api.listBigChapters(params).unWrapData().map{it.bigChapters}
    }

    override fun getMiddleChapterList(bigChapterId: Int): Single<List<MiddleChapter>> {
        val params = hashMapOf(
            "bigChapterId" to bigChapterId

        ).filterValueNotNull()

        return api.listMiddleChapters(params).unWrapData().map{it.middleChapters}
    }

    override fun getSmallChapterList(middleChapterId: Int): Single<List<SmallChapter>> {

        val params = hashMapOf(
            "middleChapterId" to middleChapterId
        ).filterValueNotNull()

        return api.listSmallChapters(params).unWrapData().map{it.smallChapters}
    }


}