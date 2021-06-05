package com.math.firstMaker.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


/**
 * created By DORO 1/3/21
 */

@Parcelize
data class ClassBelongs(
    @SerializedName("id")
    val id : Int,

    @SerializedName("student")
    val student : Student
) : Parcelable