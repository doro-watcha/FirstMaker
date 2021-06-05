package com.math.firstMaker.di

import com.math.firstMaker.AppPreference
import com.math.firstMaker.DateParserUtil
import com.math.firstMaker.utils.NoteListUtil
import com.math.firstMaker.utils.TokenUtil
import org.koin.dsl.module

val utilModule = module {
    single { DateParserUtil() }
    single { AppPreference(get())}
    single { TokenUtil(get()) }
    single { NoteListUtil(get(),get(),get()) }

}