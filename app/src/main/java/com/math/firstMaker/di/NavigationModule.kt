package com.math.firstMaker.di

import com.math.firstMaker.navigation.DialogNavigator
import com.math.firstMaker.navigation.DialogNavigatorImpl
import com.math.firstMaker.navigation.Navigator
import com.math.firstMaker.navigation.NavigatorImpl
import io.reactivex.schedulers.Schedulers.single
import org.koin.dsl.bind
import org.koin.dsl.module

val navigationModule = module {
    single { NavigatorImpl() } bind Navigator::class
    single { DialogNavigatorImpl() } bind DialogNavigator::class
}