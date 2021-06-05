package com.math.firstMaker.repository

import androidx.annotation.IntRange
import com.math.firstMaker.model.Publish
import com.math.firstMaker.model.WorkPaper
import io.reactivex.Completable
import io.reactivex.Single

interface PublishRepository {

    fun createPublish(
        title : String,
        type : String,
        sourceId : Int,
        targetUserIds : List<Int>? = null
    ) : Single<Publish>

    fun createPublishes(
        title : String,
        type : String,
        sourceId : Int,
        targetUserIds : List<Int>? = null
    ) : Completable

    fun getPublsh(
        publishId : Int
    ) : Single<Publish>

    fun updatePublish(
        publishId : Int,
        remainingTime : Int? = null,
        state : String,
        partialConfirmed : Boolean? = false

    ) : Completable

    fun listPublishesWithMaxId(
        @IntRange(from = 0L, to = Int.MAX_VALUE.toLong()) maxId : Int? = null,
        @IntRange(from = 1L, to = 50L) limit: Int? = null,
        type : String? = null,
        state : String? = null,
        userId : Int? = null,
        teacherUserId : Int? = null,
        studentUserId : Int? = null
    ) : Single<List<Publish>?>
}