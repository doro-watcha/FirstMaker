package com.math.firstMaker.repositoryImpl

import com.math.firstMaker.api.filterValueNotNull
import com.math.firstMaker.api.unWrapData
import com.math.firstMaker.api.CategoryAPI
import com.math.firstMaker.repository.CategoryRepository
import io.reactivex.Single

class CategoryRepositoryImpl (private val api : CategoryAPI) : CategoryRepository {

    override fun listCategories(
        mode: String?,
        course: String?,
        bigChapter: String?,
        middleChapter: String?
    ): Single<List<String>> {
        val params = hashMapOf(
            "mode" to mode,
            "course" to course,
            "bigChapter" to bigChapter,
            "middleChapter" to middleChapter
        ).filterValueNotNull()

        return api.listCategories(params).unWrapData().map{it.categories}

    }


}