package com.math.firstMaker.views.makeCollection.dialog

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.math.firstMaker.common.Once
import com.math.firstMaker.common.addSchedulers
import com.math.firstMaker.common.disposedBy
import com.math.firstMaker.model.Source
import com.math.firstMaker.repository.ProblemRepository
import io.reactivex.disposables.CompositeDisposable


/**
 * created By DORO 2/14/21
 */

class SourcePickerViewModel (
    private val problemRepository: ProblemRepository
) : ViewModel() {


    val sources : MutableLiveData<List<Source>> = MutableLiveData()

    val compositeDisposable = CompositeDisposable()
    val errorInvoked : MutableLiveData<Once<Throwable>> = MutableLiveData()

    init {

        listSources()

    }

    private fun listSources() {

        problemRepository.listSources()
            .addSchedulers()
            .subscribe({
                sources.value = it
            },{
                errorInvoked.value = Once(it)
            }).disposedBy(compositeDisposable)
    }

}