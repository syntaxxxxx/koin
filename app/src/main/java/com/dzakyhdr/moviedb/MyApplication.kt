package com.dzakyhdr.moviedb

import android.app.Application
import com.dzakyhdr.moviedb.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyApplication)
            // if you need context such as room, local persistence
            modules(
                listOf(
                    databaseModule,
                    networkModule,
                    repositoryModule,
                    useCaseModule,
                    viewModelModule
                )
                // define your module here
                // you can listOf() if your module 1+
            )
        }
    }

    // we can use factory<> if you create object
    // we can use single<> if you create object and make it singleton
    // we can use get() if we inject object in constructor
    
}