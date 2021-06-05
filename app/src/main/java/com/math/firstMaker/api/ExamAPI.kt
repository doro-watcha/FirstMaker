package com.math.firstMaker.api

import android.os.Parcelable
import com.math.firstMaker.model.Exam
import com.google.gson.annotations.SerializedName
import io.reactivex.Single
import kotlinx.parcelize.Parcelize
import retrofit2.http.*

interface ExamAPI {

    @GET("exam")
    fun listExams(@QueryMap parameters: HashMap<String, Any>): Single<ApiResponse<ExamListResponse>>

    @FormUrlEncoded
    @POST("exam")
    fun createExam(@FieldMap parameters: HashMap<String, Any>): Single<ApiResponse<Any>>

    @GET("exam/{id}")
    fun getExam(@Path("id") examId : Int) : Single<ApiResponse<ExamResponse>>





}

@Parcelize
data class ExamResponse(
    @SerializedName("exam")
    val exam : Exam

) : Parcelable

@Parcelize
data class ExamListResponse(

    @SerializedName("examList")
    val examList : List<Exam>?
) : Parcelable

