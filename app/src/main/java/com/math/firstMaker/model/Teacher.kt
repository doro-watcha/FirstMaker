package com.math.firstMaker.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import java.io.Serializable

@Parcelize
data class Teacher (

    @SerializedName("id")
    val id : Int,

    @SerializedName("subject")
    val subject : String,

    @SerializedName("name")
    val name : String
) : Parcelable