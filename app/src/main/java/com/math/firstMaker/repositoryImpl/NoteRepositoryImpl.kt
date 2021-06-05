package com.math.firstMaker.repositoryImpl

import com.math.firstMaker.api.NoteAPI
import com.math.firstMaker.api.ScoringResponse
import com.math.firstMaker.api.filterValueNotNull
import com.math.firstMaker.api.unWrapCompletable
import com.math.firstMaker.api.unWrapData
import com.math.firstMaker.model.Note
import com.math.firstMaker.repository.NoteRepository
import io.reactivex.Completable
import io.reactivex.Single

class NoteRepositoryImpl(private val api: NoteAPI) : NoteRepository {
    override fun createNote(publishId: Int, problemId: Int, submit: String): Completable {
        val params = hashMapOf(
            "publishId" to publishId,
            "problemId" to problemId,
            "submit" to submit
        ).filterValueNotNull()
        return api.createNote(params).unWrapCompletable()
    }


    override fun getNote(noteId: Int): Single<Note> {


        return api.getNote(noteId).unWrapData().map { it.note }
    }

    override fun scoringNote(
        submitList: List<String>?,
        noteIdList: List<Int>?,
        spendingTimeList: List<Int>?,
        greenStarClickedList : List<Boolean>?,
        wholeTime: Int,
        type: String,
        id: Int
    ): Single<ScoringResponse> {
        val params = hashMapOf(
            "submitList" to submitList,
            "noteIdList" to noteIdList,
            "spendingTimeList" to spendingTimeList,
            "greenStarClickedList" to greenStarClickedList,
            "wholeTime" to wholeTime,
            "type" to type,
            "id" to id
        ).filterValueNotNull()
        return api.updateNote(params).unWrapData()
    }

    override fun listNotes(status: String?, startDate: String?, endDate : String?): Single<List<Note>> {
        val params = hashMapOf(
            "status" to status,
            "startDate" to startDate,
            "endDate" to endDate
        ).filterValueNotNull()

        return api.listNotes(params).unWrapData().map { it.notes }
    }

    override fun deleteNote(noteId: Int): Completable {
        return api.deleteNote(noteId).unWrapCompletable()
    }

    override fun listSpecialNotes(type : String, subject: String, startDate : String, endDate : String): Single<List<Note>> {
        val params = hashMapOf(
            "subject" to subject,
            "startDate" to startDate,
            "endDate" to endDate
        ).filterValueNotNull()

        return api.listSpecialNotes(type,params).unWrapData().map{it.notes}
    }

}