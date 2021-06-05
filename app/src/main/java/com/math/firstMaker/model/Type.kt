package com.math.firstMaker.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


/**
 * created By DORO 12/20/20
 */

@Parcelize
data class Type (

    val id : Int,

    val type : String
) : Parcelable