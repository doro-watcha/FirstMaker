package com.math.firstMaker.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


/**
 * created By DORO 2/13/21
 */

@Parcelize
data class ShareRequest (

    val title : String,
    val problems : List<Problem>,
    val numChapters : Int,
    val mainChapter : String

) : Parcelable