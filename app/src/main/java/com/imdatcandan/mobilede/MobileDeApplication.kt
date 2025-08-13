package com.imdatcandan.mobilede

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class MobileDeApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MobileDeApplication)
            modules(appModule)
        }
    }
}