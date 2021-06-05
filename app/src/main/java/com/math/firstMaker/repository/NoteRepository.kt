package com.math.firstMaker.repository

import androidx.annotation.IntRange
import com.math.firstMaker.api.NoteResponse
import com.math.firstMaker.api.ScoringResponse
import com.math.firstMaker.model.Note
import io.reactivex.Completable
import io.reactivex.Single

interface NoteRepository {

    fun createNote(
        publishId : Int,
        problemId : Int,
        submit : String
    ) : Completable

    fun getNote(
        noteId : Int
    ) : Single<Note>

    fun scoringNote(
        submitList : List<String>?,
        noteIdList : List<Int>?,
        spendingTimeList : List<Int>?,
        greenStarClickedList : List<Boolean>?,
        wholeTime : Int,
        type : String,
        id : Int
    ) : Single<ScoringResponse>

    fun listNotes(
        status : String? = null,
        startDate : String? = null,
        endDate : String? = null
    ) : Single<List<Note>>

    fun listSpecialNotes (
        type : String,
        subject : String,
        startDate : String,
        endDate : String
    ) : Single<List<Note>>

    fun deleteNote(
        noteId : Int
    ) : Completable
}