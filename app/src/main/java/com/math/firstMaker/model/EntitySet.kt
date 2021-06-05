package com.math.firstMaker.model

import java.io.Serializable


/**
 * created By DORO 2020-03-31
 */

data class EntitySet (
    val id : Int,
    val entities : List<Entity>,
    val date : Date? = null
) : Serializable