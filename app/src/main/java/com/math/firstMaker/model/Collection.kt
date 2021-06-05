package com.math.firstMaker.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


/**
 * created By DORO 12/18/20
 */
@Parcelize
data class Collection (
    @SerializedName("id")
    val id : Int,

    @SerializedName("notes")
    val notes : List<Note>,

    @SerializedName("title")
    val title : String,

    @SerializedName("status")
    val status : String,

    @SerializedName("type")
    val type : String,

    @SerializedName("timeLimit")
    val timeLimit : Int?,

    @SerializedName("user")
    val user : User


) : Parcelable