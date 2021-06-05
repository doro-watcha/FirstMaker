package com.math.firstMaker.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProblemSet (

    @SerializedName("problems")
    var problems : List<Problem>,

    @SerializedName("smallChapter")
    val smallChapter : SmallChapter,

    @SerializedName("middleChapter")
    val middleChapter : MiddleChapter,

    @SerializedName("bigChapter")
    val bigChapter: BigChapter
) : Parcelable