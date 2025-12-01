package com.cuju.video

import android.app.Application
import com.cuju.videoSdk.di.videoMetaDataModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.workmanager.koin.workManagerFactory
import org.koin.core.component.KoinComponent
import org.koin.core.context.GlobalContext.startKoin

class CujuVideoApplication : Application(), KoinComponent {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@CujuVideoApplication)
            modules(videoMetaDataModule())
            workManagerFactory()
        }
    }
}
