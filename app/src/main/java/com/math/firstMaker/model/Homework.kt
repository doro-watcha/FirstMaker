package com.math.firstMaker.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.math.firstMaker.DateParserUtil
import kotlinx.parcelize.Parcelize
import org.koin.core.KoinComponent
import org.koin.core.get
import java.io.Serializable

@Parcelize
data class Homework(

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
    val updatedAt : String,

    @SerializedName("spendingTime")
    val spendingTime : Int,

    @SerializedName("teacher")
    val teacher : Teacher,

    @SerializedName("accurateRate")
    val accurateRate : Float

) : Parcelable , KoinComponent {

    fun getTime ( date : String ) : String {
        val dateParserUtil = get<DateParserUtil>()

        return dateParserUtil.parseToYMDHM(dateParserUtil.parseString(date))
    }
}