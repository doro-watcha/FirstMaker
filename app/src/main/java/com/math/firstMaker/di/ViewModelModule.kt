package com.math.firstMaker.di

import com.math.firstMaker.MainViewModel
import com.math.firstMaker.args.ProblemListArgs
import com.math.firstMaker.views.makeCollection.dialog.ChapterViewModel
import com.math.firstMaker.common.widget.bottomDialog.StudentAddViewModel
import com.math.firstMaker.model.*
import com.math.firstMaker.views.SplashViewModel
import com.math.firstMaker.views.auth.LoginViewModel
import com.math.firstMaker.views.auth.PasswordResetViewModel
import com.math.firstMaker.views.auth.signUp.SignUpViewModel
import com.math.firstMaker.views.home.HomeViewModel
import com.math.firstMaker.views.exam.ExamViewModel
import com.math.firstMaker.views.giveCollection.GiveCollectionViewModel
import com.math.firstMaker.views.home.classList.MakeClassViewModel
import com.math.firstMaker.views.home.classList.detail.ClassDetailViewModel
import com.math.firstMaker.views.home.detail.NoteDetailViewModel
import com.math.firstMaker.views.home.settingStudent.StudentSettingViewModel
import com.math.firstMaker.views.publishPage.PublishPageViewModel
import com.math.firstMaker.views.homework.HomeworkViewModel
import com.math.firstMaker.views.makeCollection.MakeCollectionViewModel
import com.math.firstMaker.views.makeCollection.SetInfoViewModel
import com.math.firstMaker.views.makeCollection.dialog.SourcePickerViewModel
import com.math.firstMaker.views.makeCollection.dialog.SubjectPickerViewModel
import com.math.firstMaker.views.makeCollection.problemSet.ProblemSetViewModel
import com.math.firstMaker.views.result.ResultViewModel
import com.math.firstMaker.views.review.ReviewViewModel
import com.math.firstMaker.views.setting.SettingViewModel
import com.math.firstMaker.views.workBook.WorkBookViewModel
import com.math.firstMaker.views.workBook.add.AddWorkBookViewModel
import com.math.firstMaker.views.workPaper.WorkPaperViewModel
import com.math.firstMaker.views.wrong.WrongStorageViewModel
import com.math.firstMaker.views.wrong.wrongExam.WrongExamViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module{
    viewModel { (type : String, id : Int ) -> PublishPageViewModel(type ,id , get(),get(),get(),get())}
    viewModel { WrongStorageViewModel(get(),get()) }
    viewModel { (publishId : Int ) -> ResultViewModel(get(), publishId) }
    viewModel { WorkPaperViewModel(get(),get()) }
    viewModel { MainViewModel(get()) }
    viewModel { ExamViewModel(get(),get()) }
    viewModel { HomeViewModel(get(),get(),get(),get(),get(),get()) }
    viewModel { HomeworkViewModel(get(),get()) }
    viewModel { MakeCollectionViewModel(get(),get(),get(),get(),get(),get(),get())}
    viewModel { (args : ProblemListArgs , type : String ) -> SetInfoViewModel ( args, type, get(),get(),get())}
    viewModel { SplashViewModel(get(),get()) }
    viewModel { (bigChapter : BigChapter) -> ChapterViewModel(bigChapter,get()) }
    viewModel { (args : ProblemListArgs) -> ReviewViewModel(args, get()) }
    viewModel { WorkBookViewModel(get(),get(),get(),get()) }
    viewModel { LoginViewModel(get()) }
    viewModel { SignUpViewModel(get()) }
    viewModel { SettingViewModel(get(),get(),get(),get()) }
    viewModel { SubjectPickerViewModel(get()) }
    viewModel { WrongExamViewModel() }
    viewModel { (collectionId : Int , type : String  ) -> NoteDetailViewModel(collectionId, type, get(),get(),get()) }
    viewModel { PasswordResetViewModel(get())}
    viewModel { SourcePickerViewModel(get())}

    //Teacher
    viewModel { StudentAddViewModel() }
    viewModel { StudentSettingViewModel() }
    viewModel { (classId : Int )-> ClassDetailViewModel(classId, get(),get(),get(),get(),get()) }
    viewModel { (problemSet : ProblemSet) -> ProblemSetViewModel(problemSet,get(),get()) }
    viewModel { ( shareRequest : ShareRequest) -> GiveCollectionViewModel ( shareRequest, get(),get(),get(),get()) }
    viewModel { MakeClassViewModel(get(),get(),get())}
    viewModel { AddWorkBookViewModel() }
}