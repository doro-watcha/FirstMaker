package com.math.firstMaker.api

import android.os.Parcelable
import com.math.firstMaker.model.Problem
import com.google.gson.annotations.SerializedName
import com.math.firstMaker.model.ProblemSet
import com.math.firstMaker.model.Source
import io.reactivex.Single
import kotlinx.parcelize.Parcelize
import retrofit2.http.*

interface ProblemAPI {

    @FormUrlEncoded
    @POST("/problem/find")
    fun listProblems(@FieldMap parameters: HashMap<String, Any>): Single<ApiResponse<ProblemListResponse>>

    @POST("/problem")
    fun createProblem(@QueryMap parameters: HashMap<String, Any>): Single<ApiResponse<Any>>

    @GET("/problem")
    fun getProblem(@Field("problemId") problemId : Int) : Single<ApiResponse<ProblemResponse>>

    @FormUrlEncoded
    @POST("/blacklist")
    fun registerBlackList(@FieldMap parameters: HashMap<String, Any> ) : Single<ApiResponse<Any>>

    @GET("/problem/replace")
    fun listReplaceProblem(@QueryMap parameters: HashMap<String,Any> ) : Single<ApiResponse<ProblemsResponse>>

    @GET("/source")
    fun listSources () : Single<ApiResponse<SourceListResponse>>

}

@Parcelize
data class ProblemResponse(
    @SerializedName("problem")
    val problem : Problem

) : Parcelable

@Parcelize
data class ProblemListResponse(

    @SerializedName("problems")
    val problems : List<Problem>,

    @SerializedName("problemSetList")
    val problemSetList : List<ProblemSet>
) : Parcelable


@Parcelize
data class ProblemsResponse(

    @SerializedName("problems")
    val problems : List<Problem>

) : Parcelable


@Parcelize
data class SourceListResponse(
    @SerializedName("sources")
    val sources : List<Source>
) : Parcelable