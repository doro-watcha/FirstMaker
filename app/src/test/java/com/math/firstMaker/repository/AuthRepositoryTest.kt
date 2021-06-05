package com.math.firstMaker.repository

import androidx.test.core.app.ApplicationProvider
import androidx.test.filters.LargeTest
import com.math.firstMaker.di.apiModule
import com.math.firstMaker.di.networkModule
import com.math.firstMaker.di.repositoryModule
import com.math.firstMaker.di.utilModule
import com.math.firstMaker.mock.fakeAuthInfo
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.module
import org.koin.test.AutoCloseKoinTest
import org.koin.test.get
import org.mockito.Mockito
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config


/**
 * created By DORO 2020/10/12
 */

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [28])
@LargeTest
class AuthRepositoryTest : AutoCloseKoinTest() {

    private lateinit var repository: AuthRepository
    private lateinit var userRepository: UserRepository


    @Before
    fun setUp() {
        startKoin {
            androidContext(ApplicationProvider.getApplicationContext())
            modules(apiModule)
            modules(networkModule)
            modules(repositoryModule)
            modules(utilModule)
        }

        repository = get()
        userRepository = get()
    }

    @Test
    fun signUpEmail() {

    }

    @Test
    fun login () {

        repository.login("goddoro@naver.com","gusgh0705!")
            .test()
            .assertNoErrors()
            .assertComplete()
            .assertValue { it.user.email == fakeAuthInfo.email }
            .dispose()
    }

    @Test
    fun signup () {

        repository.signup("goddoro@naver.com","gusgh0705!","지영고등학교",1,"3학년")
            .test()
            .assertNoErrors()
            .assertComplete()
            .dispose()

    }
}