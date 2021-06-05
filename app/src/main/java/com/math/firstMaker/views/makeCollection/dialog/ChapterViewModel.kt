package com.math.firstMaker.views.makeCollection.dialog

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.math.firstMaker.common.Once
import com.math.firstMaker.common.addSchedulers
import com.math.firstMaker.common.disposedBy
import com.math.firstMaker.debugE
import com.math.firstMaker.model.BigChapter
import com.math.firstMaker.model.MiddleChapter
import com.math.firstMaker.model.SmallChapter
import com.math.firstMaker.repository.ChapterRepository
import io.reactivex.disposables.CompositeDisposable


/**
 * created By DORO 2020/04/03
 */

class ChapterViewModel (
    private val bigChapter : BigChapter,
    val chapterRepository: ChapterRepository
) : ViewModel(){

    private val TAG = ChapterViewModel::class.java.simpleName

    private val compositeDisposable = CompositeDisposable()


    val middleChapters : MutableLiveData<List<MiddleChapter>> = MutableLiveData()

    val pickedSmallChapter = ArrayList<SmallChapter>()


    val clickConfirm : MutableLiveData<Once<Unit>> = MutableLiveData()
    val errorInvoked : MutableLiveData<Once<Throwable>> = MutableLiveData()


    init {
        middleChapters.value = bigChapter.middleChapters
    }


    fun onClickConfirm() {
        clickConfirm.value = Once(Unit)
    }

}