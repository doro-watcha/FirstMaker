package com.math.firstMaker.repository

import androidx.annotation.IntRange
import com.math.firstMaker.model.BigChapter
import com.math.firstMaker.model.WorkBook
import io.reactivex.Completable
import io.reactivex.Single


/**
 * created By DORO 2020-02-28
 */

interface WorkBookRepository {

//    fun createWorkBook(
//        title : String,
//        problemIds : List<Int>
//    ) : Completable

    fun getWorkBook(
        workBookId : Int
    ) : Single<WorkBook>

    fun listWorkBook(
        studentId : Int? = null
    ) : Single<List<WorkBook>>

    fun listMyWorkBooks (
        studentId : Int
    ) : Single <List<WorkBook>>


    fun listMyChapterList(
        studentId : Int,
        workBookId : Int
    ) : Single<List<BigChapter>>

    fun buyBigChapter (
        studentId : Int ,
        workBookId : Int,
        bigChapterId : Int
    ) : Completable



}