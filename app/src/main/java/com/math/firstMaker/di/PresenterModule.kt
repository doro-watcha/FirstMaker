package com.math.firstMaker.di

import com.math.firstMaker.views.auth.AuthPresenter
import org.koin.dsl.module



val presenterModule = module {
    factory { AuthPresenter(get(),get()) }

}