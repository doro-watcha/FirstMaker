package com.math.firstMaker.extensions

import androidx.databinding.ObservableBoolean


/**
 * created By DORO 12/17/20
 */

fun ObservableBoolean.toggle() {
    set(!get())
}
