package com.math.firstMaker.api

import android.os.Parcelable
import com.math.firstMaker.model.Note
import com.google.gson.annotations.SerializedName
import io.reactivex.Single
import kotlinx.parcelize.Parcelize
import retrofit2.http.*

interface NoteAPI {


    @GET("/note")
    fun listNotes(@QueryMap parameters: HashMap<String, Any>): Single<ApiResponse<NoteListResponse>>

    @GET("/note/list/{type}")
    fun listSpecialNotes(@Path("type") type : String, @QueryMap parameters: HashMap<String, Any> ) : Single<ApiResponse<NoteListResponse>>

    /**
     * 임시저장
     */
    @POST("/note")
    fun createNote(@Body parameters: HashMap<String, Any>): Single<ApiResponse<Any>>

    @PATCH("/note")
    fun updateNote(@Body parameters: HashMap<String, Any>) : Single<ApiResponse<ScoringResponse>>

    @GET("/note/{id}")
    fun getNote(@Path("id") noteId : Int) : Single<ApiResponse<NoteResponse>>

    @DELETE("/note")
    fun deleteNote(@Query("noteId") noteId : Int) : Single<ApiResponse<Any>>

}

@Parcelize
data class NoteResponse(
    @SerializedName("note")
    val note : Note

) : Parcelable

@Parcelize
data class NoteListResponse(

    @SerializedName("notes")
    val notes : List<Note>
) : Parcelable

@Parcelize
data class ScoringResponse(

    @SerializedName("notes")
    val notes : List<Note>,

    @SerializedName("status")
    val status : String,

    @SerializedName("accurateRate")
    val accurateRate : Float,

    @SerializedName("spendingTime")
    val spendingTime : Int
) : Parcelable