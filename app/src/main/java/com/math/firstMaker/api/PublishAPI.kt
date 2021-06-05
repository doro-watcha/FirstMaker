package com.math.firstMaker.api

import android.os.Parcelable
import com.math.firstMaker.model.Publish
import com.google.gson.annotations.SerializedName
import io.reactivex.Single
import kotlinx.parcelize.Parcelize
import retrofit2.http.*

interface PublishAPI {


    @GET("api/publishes")
    fun listPublishes(@QueryMap parameters: HashMap<String, Any>): Single<ApiResponse<PublishListResponse>>

    @POST("api/publishes")
    fun createPublish(@Body parameters: HashMap<String, Any>): Single<ApiResponse<PublishResponse>>

    @PATCH("api/publishes")
    fun updatePublish(@Query("publishId") publishId : Int, @Body parameters : HashMap<String,Any>) : Single<ApiResponse<Any>>

    @GET("api/publishes")
    @FormUrlEncoded
    fun getPublish(@Field("publishId") publishId : Int) : Single<ApiResponse<PublishResponse>>

}

@Parcelize
data class PublishResponse(
    @SerializedName("publish")
    val publish : Publish

) : Parcelable

@Parcelize
data class PublishListResponse(

    @SerializedName("publishes")
    val publishList : List<Publish>
) : Parcelable
