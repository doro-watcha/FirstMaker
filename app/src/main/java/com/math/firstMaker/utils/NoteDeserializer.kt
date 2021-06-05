package com.math.firstMaker.utils

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.math.firstMaker.model.Note
import com.math.firstMaker.model.Problem
import com.math.firstMaker.model.SmallChapter
import java.lang.reflect.Type


/**
 * created By DORO 12/17/20
 */

class NoteDeserializer : JsonDeserializer<Note> {
    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): Note {
        val jsonObject = json?.asJsonObject ?: throw NullPointerException("Response Json Note is null")

        val gson = GsonBuilder().create()

        val id = jsonObject["id"].asInt
        val problem: Problem? = gson.fromJson(jsonObject.get("problem"), Problem::class.java)
        val status = jsonObject["status"].asString
        val submit : ObservableField<String?> = ObservableField(if ( jsonObject.get("submit").isJsonNull ) "0" else jsonObject["submit"].asString)
        val spendingTime = ObservableInt(jsonObject["spendingTime"].asInt)
        val isGreenStarClicked = ObservableBoolean(jsonObject["isGreenStar"].asBoolean)
        val updatedAt = jsonObject["updatedAt"].asString


        return Note(id,problem,status,submit,spendingTime,isGreenStarClicked,updatedAt)
    }
}