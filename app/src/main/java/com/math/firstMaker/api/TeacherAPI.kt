package com.math.firstMaker.api

import android.os.Parcelable
import com.math.firstMaker.model.Teacher
import com.google.gson.annotations.SerializedName
import io.reactivex.Single
import kotlinx.parcelize.Parcelize
import retrofit2.http.*

interface TeacherAPI {


    @GET("api/teachers")
    fun listTeachers(@QueryMap parameters: HashMap<String, Any>): Single<ApiResponse<TeacherListResponse>>

    @POST("api/teachers")
    fun createTeacher(@QueryMap parameters: HashMap<String, Any>): Single<ApiResponse<Any>>

    @GET("api/Teachers")
    fun getTeacher(@Field("teacherId") teacherId : Int  ) : Single<ApiResponse<TeacherResponse>>

}

@Parcelize
data class TeacherResponse(
    @SerializedName("teacher")
    val teacher : Teacher

) : Parcelable

@Parcelize
data class TeacherListResponse(

    @SerializedName("teacherList")
    val teacherList : List<Teacher>
) : Parcelable
