package com.math.firstMaker

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.math.firstMaker.common.Once
import com.math.firstMaker.common.with
import com.math.firstMaker.repository.UserRepository
import com.math.firstMaker.AppPreference
import com.math.firstMaker.debugE
import io.reactivex.disposables.CompositeDisposable

class MainViewModel ( private val userRepository: UserRepository) :
    ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    val currentTab : MutableLiveData<Int> = MutableLiveData()

    init {
        currentTab.value = 0
    }





}