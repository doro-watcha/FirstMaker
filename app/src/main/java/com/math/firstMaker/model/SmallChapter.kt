package com.math.firstMaker.model

import android.os.Parcelable
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import com.google.gson.annotations.JsonAdapter
import com.google.gson.annotations.SerializedName
import com.math.firstMaker.utils.SmallChapterDeserializer
import kotlinx.parcelize.Parcelize
import java.io.Serializable


/**
 * created By DORO 2020-03-31
 */

@Parcelize
@JsonAdapter(SmallChapterDeserializer::class)
data class SmallChapter (

    @SerializedName("id")
    val id : Int,

    @SerializedName("name")
    val name : String,

    val isSelected : ObservableBoolean,

    var numberOfProblems : ObservableField<String>,

    var numberOfNotes : Int
) : Parcelable