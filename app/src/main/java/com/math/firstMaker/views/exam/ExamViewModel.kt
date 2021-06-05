package com.math.firstMaker.views.exam

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.math.firstMaker.common.Once
import com.math.firstMaker.common.addSchedulers
import com.math.firstMaker.common.disposedBy
import com.math.firstMaker.repository.ExamRepository
import com.math.firstMaker.model.Exam
import com.math.firstMaker.model.Publish
import com.math.firstMaker.repository.PublishRepository
import com.math.firstMaker.AppPreference
import com.math.firstMaker.DateParserUtil
import com.math.firstMaker.debugE
import com.math.firstMaker.repository.AuthRepository
import io.reactivex.disposables.CompositeDisposable
import org.koin.core.KoinComponent
import org.koin.core.inject

class ExamViewModel(
    private val publishRepository: PublishRepository,
    private val authRepository : AuthRepository
) : ViewModel(), KoinComponent {

    private val dateParserUtil: DateParserUtil by inject()
    private val compositeDisposable = CompositeDisposable()

    val publishes: MutableLiveData<List<Publish>> = MutableLiveData(listOf())

    val onLoadCompleted: MutableLiveData<Once<Unit>> = MutableLiveData()
    val onLoadFailed: MutableLiveData<Once<Throwable>> = MutableLiveData()
    val onRetake : MutableLiveData<Once<Publish>> = MutableLiveData()

    private fun listExam() {

        publishRepository.listPublishesWithMaxId(
            userId = authRepository.curUser.value?.id,
            type = "exam"
        )
            .addSchedulers()
            .subscribe({
                publishes.value = it
                onLoadCompleted.value = Once(Unit)
            }, {
                onLoadFailed.value = Once(it)
            }).disposedBy(compositeDisposable)
    }

    fun retakeExam( item : Publish) {

        publishRepository.createPublish(
            title = item.title ?: "제목없음",
            sourceId = item.exam?.id ?: 0,
            targetUserIds = listOf(authRepository.curUser.value?.id ?: 0),
            type = "exam"
        )
            .addSchedulers()
            .subscribe({
                onRetake.value = Once(it)
            },{
                onLoadFailed.value = Once(it)
            }).disposedBy(compositeDisposable)
    }

    fun refreshAction() {
        listExam()
    }


    fun getDate(date: String) = dateParserUtil.parseToYMDHM(dateParserUtil.parseString(date))

}