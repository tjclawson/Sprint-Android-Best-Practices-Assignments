package com.lambdaschool.basicandroidnetworking

import android.app.Application

class App : Application() {

    lateinit var retrofitComponent: RetrofitComponent

    override fun onCreate() {
        super.onCreate()

        retrofitComponent = DaggerRetrofitComponent.create()
    }
}