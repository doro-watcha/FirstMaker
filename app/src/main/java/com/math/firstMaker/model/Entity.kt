package com.math.firstMaker.model

import java.io.Serializable


/**
 * created By DORO 2020-03-31
 */

data class Entity (
    val id : Int,
    val type : String,
    val state : String? = null,
    val problems : List<Problem>? = null
) : Serializable