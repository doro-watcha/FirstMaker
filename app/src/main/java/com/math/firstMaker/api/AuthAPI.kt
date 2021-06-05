package com.math.firstMaker.api

import android.os.Parcelable
import com.math.firstMaker.model.User
import com.google.gson.annotations.SerializedName
import io.reactivex.Single
import kotlinx.parcelize.Parcelize
import retrofit2.http.*

interface AuthAPI {


    @POST("/auth/signin")
    @FormUrlEncoded
    fun login(@Field("email") username : String, @Field("password") password : String) : Single<ApiResponse<LoginResponse>>

    @GET("auth/me")
    fun curUser() : Single<ApiResponse<UserResponse>>


    @FormUrlEncoded
    @POST("/auth/signup")
    fun signup(@FieldMap parameters: HashMap<String, Any>): Single<ApiResponse<Any>>

    @PATCH("/auth/reset")
    fun resetPassword(
        @QueryMap parameters : HashMap<String,Any>
    ) : Single<ApiResponse<Any>>

}

@Parcelize
data class LoginResponse(
    @SerializedName("token")
    val token : String,

    @SerializedName("user")
    val user : User

) : Parcelable

@Parcelize
data class SignUpResponse(
    @SerializedName("user")
    val user : User
) : Parcelable

