package com.math.firstMaker.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import java.io.Serializable


/**
 * created By DORO 2020/04/25
 */

@Parcelize
data class Class(
    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name : String? = null,

    @SerializedName("teacher")
    val teacher : Teacher,

    @SerializedName("classBelongs")
    val classBelongs: List<ClassBelongs>?
) : Parcelable