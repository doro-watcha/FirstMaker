package com.math.firstMaker.common

import androidx.databinding.ObservableInt
import com.google.android.material.textfield.TextInputEditText

/**
 * Extension
 * -. editEmail / editPassword
 */
fun TextInputEditText.showValidateMsg(isShow: Boolean, strErrMsg: String) {
    if (isShow) this.error = strErrMsg
    else this.error = null
}


fun ObservableInt.increment() {
    set(get() + 1)
}