package com.math.firstMaker.api

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import io.reactivex.Single
import kotlinx.parcelize.Parcelize
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface CategoryAPI {

    @GET("api/categories")
    fun listCategories (@QueryMap parameters: HashMap<String, Any>) : Single<ApiResponse<CategoryResponse>>
}

@Parcelize
data class CategoryResponse(
    @SerializedName("categories")
    val categories : List<String>

) : Parcelable