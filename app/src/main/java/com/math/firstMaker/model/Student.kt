package com.math.firstMaker.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import java.io.Serializable

@Parcelize
data class Student (
    @SerializedName("id")
    val id : Int,

    @SerializedName("school")
    val school : String,

    @SerializedName("grade")
    val grade : String,

    @SerializedName("mathGrade")
    val mathGrade : String,

    @SerializedName("name")
    val name : String
) : Parcelable