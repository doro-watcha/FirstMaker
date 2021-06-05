package com.math.firstMaker.args

import android.os.Parcelable
import com.math.firstMaker.model.Problem
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProblemArgs (
    val problem : Problem
) : Parcelable