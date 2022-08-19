package com.example.jetreddit

import android.app.Application
import com.example.jetreddit.dependencyinjection.DependencyInjector

class JetRedditApplication:Application() {
    lateinit var dependencyInjector: DependencyInjector
    override fun onCreate() {
        super.onCreate()
        initDependencyInjector()
    }

    private fun initDependencyInjector() {
        dependencyInjector = DependencyInjector(this)
    }
}