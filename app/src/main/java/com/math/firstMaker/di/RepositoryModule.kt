package com.math.firstMaker.di

import com.math.firstMaker.repository.*
import com.math.firstMaker.repositoryImpl.*
import org.koin.dsl.bind
import org.koin.dsl.module

val repositoryModule = module {
    single { UserRepositoryImpl(get()) } bind UserRepository::class
    single { ExamRepositoryImpl(get()) } bind ExamRepository ::class
    single { NoteRepositoryImpl(get())} bind NoteRepository :: class
    single { ProblemRepositoryImpl(get()) } bind ProblemRepository :: class
    single { AuthRepositoryImpl(get(),get(),get(),get()) } bind AuthRepository :: class
    single { CategoryRepositoryImpl(get())} bind CategoryRepository :: class
    single { CollectionRepositoryImpl(get())} bind CollectionRepository :: class
    single { PublishRepositoryImpl(get()) } bind PublishRepository :: class
    single { StudentRepositoryImpl(get())} bind StudentRepository :: class
    single { TeacherRepositoryImpl(get())} bind TeacherRepository:: class
    single { WorkPaperRepositoryImpl(get())} bind WorkPaperRepository :: class
    single { WorkBookRepositoryImpl(get()) } bind WorkBookRepository :: class
    single { ChapterRepositoryImpl(get())} bind ChapterRepository :: class
    single { ClassRepositoryImpl(get())} bind ClassRepository :: class
    single { HomeworkRepositoryImpl(get())} bind HomeworkRepository :: class

}