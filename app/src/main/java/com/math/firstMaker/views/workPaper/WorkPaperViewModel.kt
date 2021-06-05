package com.math.firstMaker.views.workPaper

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.math.firstMaker.common.Once
import com.math.firstMaker.common.addSchedulers
import com.math.firstMaker.common.disposedBy
import com.math.firstMaker.model.Publish
import com.math.firstMaker.model.WorkPaper
import com.math.firstMaker.repository.PublishRepository
import com.math.firstMaker.repository.WorkPaperRepository
import com.math.firstMaker.AppPreference
import com.math.firstMaker.DateParserUtil
import com.math.firstMaker.debugE
import io.reactivex.disposables.CompositeDisposable
import org.koin.core.KoinComponent
import org.koin.core.inject

class WorkPaperViewModel (
    private val workPaperRepository: WorkPaperRepository,
    private val publishRepository: PublishRepository

) : ViewModel(), KoinComponent{

    private val TAG = "WorkPaperViewModel"

    private val dateParserUtil : DateParserUtil by inject()
    private val compositeDisposable = CompositeDisposable()

    val workPapers : MutableLiveData<List<WorkPaper>> = MutableLiveData(listOf())

    val publishes : MutableLiveData<List<Publish>> = MutableLiveData(listOf())

    val goPublish : MutableLiveData<Once<Publish>> = MutableLiveData()

    val onLoadCompleted : MutableLiveData<Once<Unit>> = MutableLiveData()
    val onLoadFailed : MutableLiveData<Once<Throwable>> = MutableLiveData()
    init {
        listWorkPapers()
    }

    private fun listWorkPapers () {
//
//
//        workPaperRepository.listWorkPapersWithMaxId(userId = AppPreference.userId)
//            .addSchedulers()
//            .subscribe({
//                workPapers.value = it
//                listPublishes()
//                debugE(TAG, it.map{it.id})
//            },{
//                debugE(TAG,"error from workpaper")
//                onLoadFailed.value = Once(it)
//            }).disposedBy(compositeDisposable)
    }

    private fun listPublishes(){
//        publishRepository.listPublishesWithMaxId(
//            userId = AppPreference.userId,
//            type ="workpaper")
//            .addSchedulers()
//            .subscribe({
//                publishes.value = it
//                onLoadCompleted.value = Once(Unit)
//            },{
//                debugE(TAG,"Error from publish")
//                onLoadFailed.value = Once(it)
//            }).disposedBy(compositeDisposable)

    }

    fun refreshAction () {
        listWorkPapers()
        listPublishes()
    }

    fun createWorkPaperPublish ( item : WorkPaper) {

//        debugE(TAG,item)
//
//        publishRepository.createPublish(
//            sourceId = item.id,
//            title = item.title,
//            type = "workpaper",
//            targetUserIds = listOf ( AppPreference.userId ?: 0 )
//
//        )
//            .addSchedulers()
//            .subscribe({
//                debugE(TAG, "result object = " + it)
//                goPublish(it)
//            },{
//                debugE(TAG, "Error from createWork")
//                onLoadFailed.value = Once(it)
//            }).disposedBy(compositeDisposable)

    }

    private fun goPublish( item : Publish) {



        debugE(TAG,item)
        publishRepository.updatePublish(
            publishId = item.id,
            state = "opened"
        )
            .addSchedulers()
            .subscribe({
                goPublish.value = Once(item)
            },{
                debugE(TAG,it.message)
            }).disposedBy(compositeDisposable)
    }


    fun getDate ( date : String) = dateParserUtil.parseToYMDHM(dateParserUtil.parseString(date))


}