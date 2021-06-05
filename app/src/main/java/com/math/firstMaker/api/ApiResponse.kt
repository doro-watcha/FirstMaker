package com.math.firstMaker.api

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import io.reactivex.Completable
import io.reactivex.Single
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

/**
 * Created by mj on 30, October, 2019
 */
@Parcelize
data class ApiResponse<out T>(
    @SerializedName("success")
    @Expose
    val isSuccess: Boolean,

    @SerializedName("message")
    @Expose
    val message: String?,

    @SerializedName("ecode")
    @Expose
    val errorCode: Int?,

    @SerializedName("data")
    @Expose
    val data: @RawValue T?
) : Parcelable

/**
 * 기본적인 ApiResponse 에서 data field 만 얻어내기 위한 헬퍼 메서드
 */
fun <T> Single<ApiResponse<T>>.unWrapData() =
    map {
        when (it.isSuccess) {
            true -> it.data!!

            else -> throw UnWrappingDataException(it.errorCode!!, it.message!!)
        }
    }

/**
 * 기본적인 ApiResponse 에서 data 가 전달되지 않는 success 값만 알기 위한 헬퍼 메서드
 *
 * @return Completable instance indicating api response success result
 */
fun <T> Single<ApiResponse<T>>.unWrapCompletable() : Completable =
    flatMapCompletable {
        when (it.isSuccess) {
            true -> Completable.complete()

            else -> Completable.error(UnWrappingDataException(it.errorCode!!, it.message!!))
        }
    }


class UnWrappingDataException(val errorCode: Int, message: String) : Exception(message)

fun HashMap<String, out Any?>.filterValueNotNull(): HashMap<String, Any> {
    return this.filterNot { it.value == null } as HashMap<String, Any>
}