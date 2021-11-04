package com.scan.reddit.di

import com.scan.reddit.ui.MainActivity
import com.scan.reddit.ui.viewmodel.MainActivityViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {
    fun inject(mainActivity: MainActivity)
    fun inject(viewModel: MainActivityViewModel)
}