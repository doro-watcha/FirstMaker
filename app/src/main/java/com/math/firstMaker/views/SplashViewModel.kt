package com.math.firstMaker.views

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.math.firstMaker.common.addSchedulers
import com.math.firstMaker.common.disposedBy
import com.math.firstMaker.repository.AuthRepository
import com.math.firstMaker.AppPreference
import com.math.firstMaker.common.Once
import com.math.firstMaker.common.rxRepeatTimer
import com.math.firstMaker.debugE
import com.math.firstMaker.repository.CategoryRepository
import com.math.firstMaker.repository.ChapterRepository
import io.reactivex.disposables.CompositeDisposable


class SplashViewModel (
    private val authRepository: AuthRepository,
    private val chapterRepository: ChapterRepository
) : ViewModel() {

    private val TAG = SplashViewModel::class.java.simpleName

    private val compositeDisposable = CompositeDisposable()

    val screenOver : MutableLiveData<Once<Unit>> = MutableLiveData()
    val errorInvoked : MutableLiveData<Once<Throwable>> = MutableLiveData()


    init {

    }


    fun waitSplashScreen () {


        chapterRepository.getSubjectList()
            .addSchedulers()
            .subscribe({
                screenOver.value = Once(Unit)
            },{
                errorInvoked.value = Once(it)
            }).disposedBy(compositeDisposable)

    }

}