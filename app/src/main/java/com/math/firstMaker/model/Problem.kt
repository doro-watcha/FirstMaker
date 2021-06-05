package com.math.firstMaker.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Problem(

        @SerializedName("id")
        val id: Int,

        @SerializedName("problemUrl")
        val problemUrl : String?,

        @SerializedName("solutionUrl")
        val solutionUrl : String? = null ,

        @SerializedName("answer")
        val answer : String?,

        @SerializedName("level")
        val level : Int?,

        @SerializedName("bigChapter")
        val bigChapter : BigChapter,

        @SerializedName("middleChapter")
        val middleChapter : MiddleChapter,

        @SerializedName("smallChapter")
        val smallChapter : SmallChapter,

        @SerializedName("subject")
        val subject : Subject?,

        @SerializedName("isMultipleQuestion")
        val isMultipleQuestion : Int? = 0
) : Parcelable