package com.math.firstMaker.repositoryImpl

import com.math.firstMaker.api.filterValueNotNull
import com.math.firstMaker.api.unWrapCompletable
import com.math.firstMaker.api.unWrapData
import com.math.firstMaker.api.PublishAPI
import com.math.firstMaker.model.Publish
import com.math.firstMaker.repository.PublishRepository
import io.reactivex.Completable
import io.reactivex.Single

class PublishRepositoryImpl (private val api: PublishAPI) : PublishRepository {
    override fun createPublish(
        title: String,
        type: String,
        sourceId: Int,
        targetUserIds : List<Int>?

    ): Single<Publish> {
        val params = hashMapOf(
            "title" to title,
            "type" to type,
            "sourceId" to sourceId,
            "targetUserIds" to targetUserIds
        ).filterValueNotNull()
        return api.createPublish(params).unWrapData().map{it.publish}
    }

    override fun createPublishes(
        title: String,
        type: String,
        sourceId: Int,
        targetUserIds: List<Int>?
    ): Completable {
        val params = hashMapOf(
            "title" to title,
            "type" to type,
            "sourceId" to sourceId,
            "targetUserIds" to targetUserIds

        ).filterValueNotNull()
        return api.createPublish(params).unWrapCompletable()
    }


    override fun getPublsh(publishId: Int): Single<Publish> {
        return api.getPublish(publishId).unWrapData().map{it.publish}
    }

    override fun updatePublish(publishId: Int, remainingTime: Int?, state: String , partialConfirmed : Boolean?) : Completable{
        val params = hashMapOf(
            "remainingTime" to remainingTime,
            "state" to state,
            "partial-confirmed" to partialConfirmed
        ).filterValueNotNull()

        return api.updatePublish(publishId, params).unWrapCompletable()
}

    override fun listPublishesWithMaxId(maxId: Int?, limit: Int?, type: String?, state : String?, userId : Int?, teacherUserId : Int?, studentUserId : Int?): Single<List<Publish>?> {
        val params = hashMapOf(
            "maxId" to maxId,
            "limit" to limit,
            "type" to type,
            "state" to state,
            "userId" to userId,
            "teacherUserId" to teacherUserId,
            "studentUserId" to studentUserId
        ).filterValueNotNull()
        return api.listPublishes(params).unWrapData().map{it.publishList}
    }


}