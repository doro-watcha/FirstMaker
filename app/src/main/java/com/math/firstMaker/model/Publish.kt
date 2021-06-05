package com.math.firstMaker.model

import java.io.Serializable

data class Publish (
    val id : Int,
    val exam : Exam?,
    val title : String?,
    val homework : Homework?,
    val workpaper : WorkPaper?,
    val workbook : WorkBook?,
    val remainingTime : Int?,
    var state : String,
    var startPosition : Int,
    val createdAt : String?,
    val teacher : Teacher
) : Serializable