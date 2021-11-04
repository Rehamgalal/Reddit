package com.scan.reddit

import android.app.Application
import com.scan.reddit.di.AppComponent
import com.scan.reddit.di.AppModule
import com.scan.reddit.di.DaggerAppComponent

class Reddit : Application() {
    private lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder().appModule(AppModule(this)).build()
    }

    fun getAppComponent(): AppComponent {
        return appComponent
    }
}