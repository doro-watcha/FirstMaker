package com.math.firstMaker.utils

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.math.firstMaker.model.SmallChapter
import java.lang.reflect.Type


/**
 * created By DORO 12/17/20
 */

class SmallChapterDeserializer : JsonDeserializer<SmallChapter> {
    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): SmallChapter {
        val jsonObject = json?.asJsonObject ?: throw NullPointerException("Response Json SmallChapter is null")

        val id = jsonObject["id"].asInt
        val name = jsonObject["name"].asString
        val isSelected = ObservableBoolean(false)
        val numberOfProblems = ObservableField<String>("1")


        return SmallChapter(id,name,isSelected,numberOfProblems,1)
    }
}