package com.lambdaschool.basicandroidnetworking

import com.lambdaschool.basicandroidnetworking.retrofit.RetrofitActivity
import dagger.Component
import retrofit2.Retrofit
import javax.inject.Singleton

@Singleton
@Component(modules = [RetrofitModule::class])
interface RetrofitComponent {

    fun inject(retrofitActivity: RetrofitActivity)

    //fun getRetrofitInstance(): Retrofit
}