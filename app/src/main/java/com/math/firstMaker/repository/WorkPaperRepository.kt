package com.math.firstMaker.repository

import androidx.annotation.IntRange
import com.math.firstMaker.model.WorkPaper
import io.reactivex.Completable
import io.reactivex.Single

interface WorkPaperRepository {

    fun createWorkPaper(
        title : String,
        problemIds : List<Int>,
        numChapters : Int,
        mainChapter : String
    ) : Completable

    fun getWorkPaper(
        workPaperId : Int
    ) : Single<WorkPaper>


    fun listWorkPapers(

    ) : Single<List<WorkPaper>>
}