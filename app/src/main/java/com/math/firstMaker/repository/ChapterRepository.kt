package com.math.firstMaker.repository

import com.math.firstMaker.model.BigChapter
import com.math.firstMaker.model.MiddleChapter
import com.math.firstMaker.model.SmallChapter
import com.math.firstMaker.model.Subject
import io.reactivex.Single


/**
 * created By DORO 2020/11/01
 */

interface ChapterRepository {

    fun getSubjectList (

    ) : Single<List<Subject>>

    fun getBigChapterList (
        subjectId : Int
    ) : Single<List<BigChapter>>

    fun getMiddleChapterList (
        bigChapterId : Int
    ) : Single<List<MiddleChapter>>


    fun getSmallChapterList (
        middleChapterId : Int
    ) : Single<List<SmallChapter>>

}
