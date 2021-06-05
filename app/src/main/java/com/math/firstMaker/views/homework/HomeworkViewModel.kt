package com.math.firstMaker.views.homework

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.math.firstMaker.common.Once
import com.math.firstMaker.common.addSchedulers
import com.math.firstMaker.common.disposedBy
import com.math.firstMaker.model.Homework
import com.math.firstMaker.model.Publish
import com.math.firstMaker.AppPreference
import com.math.firstMaker.DateParserUtil
import com.math.firstMaker.debugE
import com.math.firstMaker.repository.*
import io.reactivex.disposables.CompositeDisposable
import org.koin.core.KoinComponent
import org.koin.core.inject

class HomeworkViewModel(

    private val examRepository : ExamRepository,
    private val authRepository : AuthRepository
) : ViewModel(), KoinComponent {


    private val dateParserUtil: DateParserUtil by inject()
    private val compositeDisposable = CompositeDisposable()

    val onLoadCompleted: MutableLiveData<Once<Unit>> = MutableLiveData()
    val onLoadFailed: MutableLiveData<Once<Throwable>> = MutableLiveData()





}