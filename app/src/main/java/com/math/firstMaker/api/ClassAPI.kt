package com.math.firstMaker.api

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.math.firstMaker.model.Class
import io.reactivex.Single
import kotlinx.parcelize.Parcelize
import retrofit2.http.*


/**
 * created By DORO 1/3/21
 */

interface ClassAPI {

    @GET("class")
    fun listClasses(@QueryMap parameters: HashMap<String, Any>): Single<ApiResponse<ClassListResponse>>

    @POST("class")
    fun createClass(@Body parameters: HashMap<String, Any>): Single<ApiResponse<Any>>

    @GET("class/{id}")
    fun getClass(@Path("id") classId : Int) : Single<ApiResponse<ClassResponse>>

    @GET("class/{studentId}/list")
    fun listClassesByStudentId ( @Path("studentId") studentId : Int) : Single<ApiResponse<ClassListResponse>>

    @POST("class/addStudent")
    fun addStudent(@Body parameters : HashMap<String,Any>) : Single<ApiResponse<Any>>

    @POST("class/delete/student")
    fun deleteStudent(@Body parameters : HashMap<String,Any>) : Single<ApiResponse<Any>>

}

@Parcelize
data class ClassResponse(

    @SerializedName("class")
    val classInfo : Class

) : Parcelable

@Parcelize
data class ClassListResponse(

    @SerializedName("classes")
    val classes : List<Class>?

) : Parcelable

