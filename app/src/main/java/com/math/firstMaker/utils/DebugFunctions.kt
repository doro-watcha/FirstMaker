package com.math.firstMaker

import android.util.Log
import com.math.firstMaker.BuildConfig

fun debugE(tag: String, message: Any?) {
    if (BuildConfig.DEBUG)
        Log.e(tag, "\uD83C\uDF40" + message.toString())
}

fun debugE(message: Any?) {
    debugE("DEBUG", message)
}