package com.math.firstMaker.api


import android.os.Parcelable
import com.math.firstMaker.model.User
import com.google.gson.annotations.SerializedName
import io.reactivex.Single
import kotlinx.parcelize.Parcelize
import retrofit2.http.*

interface UserAPI {

    @GET("user/searchStudent")
    fun listUsersByStudentName(
        @Query("studentName") studentName : String ) : Single<ApiResponse<SearchStudentListResponse>>

}

@Parcelize
data class UserResponse(
    @SerializedName("user")
    val user : User

) : Parcelable

@Parcelize
data class UserListResponse(

    @SerializedName("users")
    val userList : List<User>
) : Parcelable

@Parcelize
data class SearchStudentListResponse(

    @SerializedName("students")
    val students : List<User>
) : Parcelable