package com.math.firstMaker.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import java.io.Serializable

@Parcelize
data class User (

    @SerializedName("id")
    val id : Int,

    @SerializedName("name")
    val name : String,

    @SerializedName("email")
    val email : String,



    @SerializedName("type")
    val type : String,

    @SerializedName("teacher")
    val teacher : Teacher?,

    @SerializedName("student")
    val student : Student?

): Parcelable

