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
data class MiddleChapter (

    @SerializedName("id")
    val id : Int,

    @SerializedName("name")
    val name : String,

    @SerializedName("smallChapter")
    var smallChapters : List<SmallChapter>?

) : Parcelable {

    fun isSelected () : Boolean {

        if (( smallChapters?.size ?: 0) > 0) {
            smallChapters?.forEach {
                if (!it.isSelected.get()) return false
            }

            return true
        } else {
            return false
        }
    }
}