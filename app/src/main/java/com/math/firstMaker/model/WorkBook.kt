package com.math.firstMaker.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable


/**
 * created By DORO 2020-02-28
 */

data class WorkBook (
    @SerializedName("id")
    val id : Int,

    @SerializedName("title")
    val title : String,

    @SerializedName("publisher")
    val publisher : String,

    @SerializedName("problems")
    val problems : List<Problem>? = null,

    @SerializedName("subject")
    val subject : Subject

) : Serializable