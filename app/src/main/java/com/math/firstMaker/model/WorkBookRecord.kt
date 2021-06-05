package com.math.firstMaker.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


/**
 * created By DORO 1/13/21
 */

@Parcelize
data class WorkBookRecord(

    @SerializedName("id")
    val id : Int,

    @SerializedName("student")
    val student : Student,

    @SerializedName("workBook")
    val workBook : WorkBook,

    @SerializedName("subject")
    val subject : List<Subject>

) : Parcelable