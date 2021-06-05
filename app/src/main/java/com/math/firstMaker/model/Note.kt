package com.math.firstMaker.model

import android.os.Parcelable
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import com.google.gson.annotations.JsonAdapter
import com.google.gson.annotations.SerializedName
import com.math.firstMaker.utils.NoteDeserializer
import com.math.firstMaker.utils.SmallChapterDeserializer
import kotlinx.parcelize.Parcelize
import java.io.Serializable

@Parcelize
@JsonAdapter(NoteDeserializer::class)
data class Note(

    @SerializedName("id")
    val id :Int,

    @SerializedName("problem")
    val problem : Problem? = null,

    @SerializedName("status")
    var status : String,

    val submit : ObservableField<String?>,

    val spendingTime : ObservableInt,

    val isGreenStarClicked : ObservableBoolean,

    @SerializedName("updatedAt")
    val updatedAt : String
): Parcelable