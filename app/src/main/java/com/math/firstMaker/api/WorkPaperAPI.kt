package com.math.firstMaker.api

import android.os.Parcelable
import com.math.firstMaker.model.WorkPaper
import com.google.gson.annotations.SerializedName
import io.reactivex.Single
import kotlinx.parcelize.Parcelize
import retrofit2.http.*

interface WorkPaperAPI {

    @GET("/workPaper")
    fun listWorkPapers(): Single<ApiResponse<WorkPaperListResponse>>

    @POST("/workPaper")
    fun createWorkPaper(@Body parameters: HashMap<String, Any>): Single<ApiResponse<Any>>

    @GET("/workPaper/{id}")
    fun getWorkPaper(@Path("id") id : Int) : Single<ApiResponse<WorkPaperResponse>>


}

@Parcelize
data class WorkPaperResponse(
    @SerializedName("workPaper")
    val workPaper : WorkPaper

) : Parcelable

@Parcelize
data class WorkPaperListResponse(

    @SerializedName("workPapers")
    val workPaperList : List<WorkPaper>
) : Parcelable

