package com.wildan.subs1intermediet.base

import android.app.Application
import com.wildan.subs1intermediet.Module.moduleAuth
import com.wildan.subs1intermediet.Module.moduleNetwork
import com.wildan.subs1intermediet.Module.modulePreference
import com.wildan.subs1intermediet.Module.moduleStory
import com.wildan.subs1intermediet.Module.moduleViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class BaseApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        //inisiasi koin
        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@BaseApplication)
            modules(
                listOf(
                    moduleAuth,
                    moduleStory,
                    moduleNetwork,
                    modulePreference,
                    moduleViewModel,
                )
            )
        }
    }
}