package com.math.firstMaker

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import com.math.firstMaker.di.*
import io.reactivex.exceptions.UndeliverableException
import io.reactivex.plugins.RxJavaPlugins
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import java.io.IOException
import java.net.SocketException

class MainApplication : Application() {
    companion object {
        private lateinit var mainApp: MainApplication
        val context: Context by lazy {
            mainApp.applicationContext
        }

        var is_BeatFlo_FG = false
    }

    override fun onCreate() {
        super.onCreate()

        mainApp = this
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        registerActivityLifecycleCallbacks(mActivityLifecycleCallbacks)

        inject()
    }

    private fun inject() {

        startKoin {
            androidContext(this@MainApplication)
            androidLogger(Level.INFO)
            modules(
                listOf(
                    networkModule,
                    apiModule,
                    presenterModule,
                    viewModelModule,
                    repositoryModule,
                    navigationModule,
                    utilModule
                )
            )
        }
    }


    internal var mActivityLifecycleCallbacks: Application.ActivityLifecycleCallbacks =
        object : Application.ActivityLifecycleCallbacks {
            private var numStarted = 0
            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {

            }

            override fun onActivityStarted(activity: Activity) {


                if (numStarted == 0) {
                    // app went to foreground
                    is_BeatFlo_FG = true
                }
                numStarted++
            }

            override fun onActivityResumed(activity: Activity) {

            }

            override fun onActivityPaused(activity: Activity) {

            }

            override fun onActivityStopped(activity: Activity) {
                numStarted--

                if (numStarted == 0) {
                    // app went to background
                    // application의 terminate는 에뮬에서만 동작한다.
                    // 앱이 종료되는지 여부는 main activity가 destroy되는걸로 판단한다.
                    is_BeatFlo_FG = false

                }
            }

            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {

            }

            override fun onActivityDestroyed(activity: Activity) {}
        }
}
