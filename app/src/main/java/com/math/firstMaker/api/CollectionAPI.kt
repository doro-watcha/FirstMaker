package com.math.firstMaker.api

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.math.firstMaker.model.Collection
import io.reactivex.Single
import kotlinx.parcelize.Parcelize
import retrofit2.http.*


interface CollectionAPI {

    @GET("/collection")
    fun listCollections(@QueryMap parameters: HashMap<String, Any>): Single<ApiResponse<CollectionListResponse>>

    @FormUrlEncoded
    @POST("/collection")
    fun createCollection(@FieldMap parameters: HashMap<String, Any>): Single<ApiResponse<CollectionResponse>>

    @GET("/collection/{id}")
    fun getCollection(@Path("id") collectionId : Int) : Single<ApiResponse<CollectionResponse>>

}

@Parcelize
data class CollectionResponse(
    @SerializedName("collection")
    val collection : Collection

) : Parcelable

@Parcelize
data class CollectionListResponse(

    @SerializedName("collections")
    val collections : List<Collection>?
) : Parcelable

