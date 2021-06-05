package com.math.firstMaker.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


/**
 * created By DORO 2020/11/01
 */

@Parcelize
data class Subject (

    @SerializedName("id")
    val id : Int,

    @SerializedName("name")
    val name : String,

    @SerializedName("bigChapter")
    val bigChapters : List<BigChapter>? = null
) : Parcelable