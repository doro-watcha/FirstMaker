package com.math.firstMaker.views.makeCollection.dialog

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.math.firstMaker.common.Once
import com.math.firstMaker.common.addSchedulers
import com.math.firstMaker.common.disposedBy
import com.math.firstMaker.debugE
import com.math.firstMaker.model.Subject
import com.math.firstMaker.repository.ChapterRepository
import io.reactivex.disposables.CompositeDisposable


/**
 * created By DORO 2020/11/01
 */

class SubjectPickerViewModel (
    private val chapterRepository : ChapterRepository
): ViewModel() {

    private val TAG = SubjectPickerViewModel::class.java.simpleName

    private val compositeDisposable = CompositeDisposable()

    val subjects : MutableLiveData<List<Subject>> = MutableLiveData()

    val errorInvoked : MutableLiveData<Once<Throwable>> = MutableLiveData()

    init {

        getSubjectList()
    }

    private fun getSubjectList () {

        chapterRepository.getSubjectList()
            .addSchedulers()
            .subscribe({
                subjects.value = it
                debugE(TAG, it.map{it.name})
            },{
                errorInvoked.value = Once(it)
            }).disposedBy(compositeDisposable)
    }
}