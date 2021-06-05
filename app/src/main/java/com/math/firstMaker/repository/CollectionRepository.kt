package com.math.firstMaker.repository

import androidx.annotation.IntRange
import com.math.firstMaker.model.Collection
import com.math.firstMaker.model.Homework
import io.reactivex.Completable
import io.reactivex.Single

interface CollectionRepository {


    fun createCollection (
        title : String,
        type : String,
        problemIdList : List<Int>,
        timeLimit : Int?
    ) : Single<Collection>

    fun listCollections (
        page : Int
    ) : Single<List<Collection>>

    fun getCollection (
        collectionId : Int
    ) : Single<Collection>

}