package com.math.firstMaker.di

import com.math.firstMaker.api.*
import com.math.firstMaker.model.Publish
import org.koin.dsl.module
import retrofit2.Retrofit

val apiModule = module {
    single { get<Retrofit>().create(UserAPI::class.java) }
    single { get<Retrofit>().create(ProblemAPI::class.java) }
    single { get<Retrofit>().create(ExamAPI::class.java) }
    single { get<Retrofit>().create(NoteAPI::class.java) }
    single { get<Retrofit>().create(CollectionAPI::class.java) }
    single { get<Retrofit>().create(WorkPaperAPI::class.java) }
    single { get<Retrofit>().create(TeacherAPI::class.java) }
    single { get<Retrofit>().create(StudentAPI::class.java) }
    single { get<Retrofit>().create(PublishAPI::class.java)}
    single { get<Retrofit>().create(CategoryAPI::class.java)}
    single { get<Retrofit>().create(AuthAPI::class.java)}
    single { get<Retrofit>().create(WorkBookAPI::class.java)}
    single { get<Retrofit>().create(ChapterAPI::class.java)}
    single { get<Retrofit>().create(ClassAPI::class.java)}
    single { get<Retrofit>().create(HomeworkAPI::class.java)}

}