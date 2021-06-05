package com.math.firstMaker.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


/**
 * created By DORO 2/14/21
 */

@Parcelize
data class Source (
    @SerializedName("id")
    val id : Int,

    @SerializedName("name")
    val name : String
) : Parcelable