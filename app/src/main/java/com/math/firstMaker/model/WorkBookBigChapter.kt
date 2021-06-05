package com.math.firstMaker.model

import android.os.Parcelable
import androidx.databinding.ObservableBoolean
import kotlinx.parcelize.Parcelize


/**
 * created By DORO 1/26/21
 */

@Parcelize
data class WorkBookBigChapter (
    
    val bigChapter : BigChapter,
    
    val isMyChapter : ObservableBoolean

) : Parcelable