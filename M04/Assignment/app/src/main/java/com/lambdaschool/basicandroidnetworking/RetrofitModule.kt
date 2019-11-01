package com.lambdaschool.basicandroidnetworking

import com.lambdaschool.basicandroidnetworking.model.AdviceMsg
import com.lambdaschool.basicandroidnetworking.retrofit.AdviceAPI
import dagger.Module
import dagger.Provides
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class RetrofitModule {

    companion object {
        internal const val BASE_URL = "https://api.adviceslip.com/"
    }

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {

        return Retrofit
            .Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
    }

    @Provides
    fun provideAdvice(retrofit: Retrofit): Call<AdviceMsg> {
        return retrofit.create(AdviceAPI::class.java).randomAdvice()
    }
}