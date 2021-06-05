package com.math.firstMaker.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable


/**
 * created By DORO 12/21/20
 */

data class WrongPaper (
    @SerializedName("id")
    val id : Int,

    @SerializedName("title")
    val title : String,

    @SerializedName("notes")
    val notes : List<Note>,

    @SerializedName("status")
    val status : String

) : Serializable