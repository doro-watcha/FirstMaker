package com.math.firstMaker.repository

import io.reactivex.Single

interface CategoryRepository {

    fun listCategories (
        mode : String? = null,
        course : String? = null,
        bigChapter : String? = null,
        middleChapter : String? = null
    ) : Single<List<String>>
}