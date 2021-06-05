package com.math.firstMaker.api

import android.os.Parcelable
import com.math.firstMaker.model.Student
import com.google.gson.annotations.SerializedName
import io.reactivex.Single
import kotlinx.parcelize.Parcelize
import retrofit2.http.*

interface StudentAPI {


    @GET("student")
    fun listStudents(@QueryMap parameters: HashMap<String, Any>): Single<ApiResponse<StudentListResponse>>

    @FormUrlEncoded
    @POST("student")
    fun addStudent(@Field("teacherId") teacherId : Int, @Field("studentId") studentId : Int ) : Single<ApiResponse<StudentResponse>>

}

@Parcelize
data class StudentResponse(
    @SerializedName("student")
    val student : Student

) : Parcelable

@Parcelize
data class StudentListResponse(

    @SerializedName("studentList")
    val studentList : List<Student>
) : Parcelable
