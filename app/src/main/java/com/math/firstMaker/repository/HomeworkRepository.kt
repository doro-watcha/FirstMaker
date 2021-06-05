package com.math.firstMaker.repository

import com.math.firstMaker.model.Homework
import io.reactivex.Completable
import io.reactivex.Single


/**
 * created By DORO 1/4/21
 */

interface HomeworkRepository {


    fun getHomework (
        homeworkId : Int
    ) : Single<Homework>

    fun createHomework(
        title : String,
        problemIds : List<Int>,
        numChapters : Int,
        mainChapter : String,
        userIdList : List<Int>
    ) : Completable

    fun listHomeworkList (
        userId : Int
    ) : Single<List<Homework>>
}