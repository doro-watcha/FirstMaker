package com.math.firstMaker.common.widget.bottomDialog

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.math.firstMaker.model.Class


/**
 * created By DORO 2020/05/11
 */

class StudentAddViewModel : ViewModel() {



    val classes : MutableLiveData<List<Class>> = MutableLiveData()

    init {

        classes.value = listOf(
        )
    }
}