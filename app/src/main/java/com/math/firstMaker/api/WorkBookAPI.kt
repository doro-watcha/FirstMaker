package com.math.firstMaker.api

import android.os.Parcelable
import com.math.firstMaker.model.WorkBook
import com.google.gson.annotations.SerializedName
import io.reactivex.Single
import kotlinx.parcelize.Parcelize
import retrofit2.http.*


/**
 * created By DORO 2020-02-28
 */

interface WorkBookAPI {

    @GET("workbook")
    fun listWorkBooks(@QueryMap parameters: HashMap<String, Any>): Single<ApiResponse<WorkBookListResponse>>

    @GET("workbook/{studentId}/list")
    fun listMyWorkBooks ( @Path("studentId") studentId : Int  ) : Single<ApiResponse<WorkBookListResponse>>

    @POST("workbook")
    fun createWorkBook(@Body parameters: HashMap<String, Any>): Single<ApiResponse<Any>>

    @GET("workbook/{id}")
    fun getWorkBook(@Path("id") workbookId : Int) : Single<ApiResponse<WorkBookResponse>>

    @GET("workbook/myChapter/{studentId}/list")
    fun listMyChapterList(@Path("studentId") studentId :Int ,@QueryMap parameters: HashMap<String, Any>) : Single<ApiResponse<BigChapterListResponse>>

    @FormUrlEncoded
    @POST("workbook/buy")
    fun buyBigChapter(@FieldMap parameters: HashMap<String,Any>) : Single<ApiResponse<Any>>

}

@Parcelize
data class WorkBookResponse(
    @SerializedName("workBook")
    val workBook : WorkBook

) : Parcelable

@Parcelize
data class WorkBookListResponse(

    @SerializedName("workBooks")
    val workBookList : List<WorkBook>
) : Parcelable

