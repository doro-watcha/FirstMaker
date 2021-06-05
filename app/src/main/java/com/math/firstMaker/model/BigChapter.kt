package com.math.firstMaker.model

import android.os.Parcelable
import androidx.databinding.ObservableBoolean
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import java.io.Serializable


/**
 * created By DORO 2020-03-31
 */

@Parcelize
data class BigChapter(
    @SerializedName("id")
    val id : Int,

    @SerializedName("name")
    val name : String,

    @SerializedName("middleChapter")
    var middleChapters : List<MiddleChapter>?,

    @SerializedName("numMiddle")
    val numMiddle : Int,

    @SerializedName("numSmall")
    val numSmall : Int
) : Parcelable