package com.math.firstMaker.model

import java.io.Serializable


/**
 * created By DORO 2020-03-31
 */

data class Date (
    val id : Int,
    val date : String,
    val dayOfWeek : String
) : Serializable