package com.math.firstMaker.views.result

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.math.firstMaker.common.Once

import com.math.firstMaker.common.addSchedulers
import com.math.firstMaker.common.disposedBy
import com.math.firstMaker.repository.NoteRepository
import com.math.firstMaker.model.Note
import io.reactivex.disposables.CompositeDisposable


class ResultViewModel(
    private val noteRepository: NoteRepository,
    private val publishId: Int
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    private val _notes: MutableLiveData<List<Note>?> = MutableLiveData(listOf())
    val notes: LiveData<List<Note>?>
        get() = _notes

    private val _errorInvoked: MutableLiveData<Once<Throwable>> = MutableLiveData()
    val errorInvoked: LiveData<Once<Throwable>>
        get() = _errorInvoked


    init {
        resultList()
    }

    private fun resultList() {
//        noteRepository.listNotesWithMaxId(
//            publishId = publishId
//        )
//            .addSchedulers()
//            .subscribe({
//                _notes.value = it
//            }, {
//                _errorInvoked.value = Once(it)
//            }).disposedBy(compositeDisposable)

    }
}