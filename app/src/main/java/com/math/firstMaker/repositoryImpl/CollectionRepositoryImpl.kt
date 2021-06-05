package com.math.firstMaker.repositoryImpl

import com.math.firstMaker.api.CollectionAPI
import com.math.firstMaker.api.filterValueNotNull
import com.math.firstMaker.api.unWrapData
import com.math.firstMaker.model.Collection
import com.math.firstMaker.repository.CollectionRepository
import io.reactivex.Single

class CollectionRepositoryImpl(private val api: CollectionAPI) : CollectionRepository {

    override fun createCollection(
        title: String,
        type: String,
        problemIdList: List<Int>,
        timeLimit: Int?
    ): Single<Collection> {
        val params = hashMapOf(
            "title" to title,
            "type" to type,
            "problemIdList" to problemIdList,
            "timeLimit" to timeLimit
        ).filterValueNotNull()

        return api.createCollection(params).unWrapData().map { it.collection }
    }

    override fun listCollections( page : Int): Single<List<Collection>> {
        val params = hashMapOf(
            "page" to page

        ).filterValueNotNull()

        return api.listCollections(params).unWrapData().map{ it.collections}
    }

    override fun getCollection(collectionId: Int): Single<Collection> {
        return api.getCollection(collectionId).unWrapData().map { it.collection }
    }


}