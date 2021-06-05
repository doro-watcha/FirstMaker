package com.math.firstMaker.api

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.math.firstMaker.model.Homework
import io.reactivex.Single
import kotlinx.parcelize.Parcelize
import retrofit2.http.*


/**
 * created By DORO 1/4/21
 */

interface HomeworkAPI {


    @GET("/homework")
    fun listHomeworkList(@QueryMap parameters: HashMap<String, Any>): Single<ApiResponse<HomeworkListResponse>>

    @GET("/homework/{id}")
    fun getHomework(@Path("id") homeworkId : Int) : Single<ApiResponse<HomeworkResponse>>

    @POST("/homework")
    fun createHomework(@Body parameters: HashMap<String, Any>): Single<ApiResponse<Any>>
}

@Parcelize
data class HomeworkResponse(
    @SerializedName("homework")
    val homework : Homework

) : Parcelable

@Parcelize
data class HomeworkListResponse(

    @SerializedName("homeworkList")
    val homeworkList : List<Homework>?
) : Parcelable