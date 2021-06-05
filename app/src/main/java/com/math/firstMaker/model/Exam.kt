package com.math.firstMaker.model

import com.google.gson.annotations.SerializedName
import com.math.firstMaker.DateParserUtil
import org.koin.core.KoinComponent
import org.koin.core.get
import java.io.Serializable

data class Exam (

    @SerializedName("id")
    val id : Int,

    @SerializedName("title")
    val title : String,

    @SerializedName("note")
    val notes : List<Note>?,

    @SerializedName("status")
    val status : String,

    @SerializedName("numChapters")
    val numChapters : Int,

    @SerializedName("mainChapter")
    val mainChapter : String,

    @SerializedName("createdAt")
    val createdAt : String,

    @SerializedName("updatedAt")
    val updatedAt  : String,

    @SerializedName("teacher")
    val teacher : Teacher,

    @SerializedName("timeLimit")
    val timeLimit : Int,

    @SerializedName("spendingTime")
    val spendingTime : Int,

    @SerializedName("accurateRate")
    val accurateRate : Float

): Serializable  , KoinComponent {

    fun getTime ( date : String ) : String {
        val dateParserUtil = get<DateParserUtil>()

        return dateParserUtil.parseToYMDHM(dateParserUtil.parseString(date))
    }
}