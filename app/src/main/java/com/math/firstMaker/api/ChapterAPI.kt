package com.math.firstMaker.api

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.math.firstMaker.model.BigChapter
import com.math.firstMaker.model.MiddleChapter
import com.math.firstMaker.model.SmallChapter
import com.math.firstMaker.model.Subject
import io.reactivex.Single
import kotlinx.parcelize.Parcelize
import retrofit2.http.GET
import retrofit2.http.QueryMap


/**
 * created By DORO 2020/11/01
 */

interface ChapterAPI {

    @GET("subject")
    fun listSubjects (@QueryMap parameters: HashMap<String, Any>) : Single<ApiResponse<SubjectListResponse>>

    @GET("bigChapter")
    fun listBigChapters ( @QueryMap parameters: HashMap<String,Any>) : Single<ApiResponse<BigChapterListResponse>>

    @GET("middleChapter")
    fun listMiddleChapters ( @QueryMap parameters : HashMap<String,Any>) : Single<ApiResponse<MiddleChapterListResponse>>

    @GET("smallChapter")
    fun listSmallChapters ( @QueryMap parameters : HashMap<String,Any>) : Single<ApiResponse<SmallChapterListResponse>>
}

@Parcelize
data class SubjectListResponse(
    @SerializedName("subjects")
    val subjects : List<Subject>

) : Parcelable


@Parcelize
data class BigChapterListResponse(
    @SerializedName("bigChapters")
    val bigChapters : List<BigChapter>
) : Parcelable

@Parcelize
data class MiddleChapterListResponse(

    @SerializedName("middleChapters")
    val middleChapters : List<MiddleChapter>
) : Parcelable

@Parcelize
data class SmallChapterListResponse(

    @SerializedName("smallChapters")
    val smallChapters : List<SmallChapter>
) : Parcelable

