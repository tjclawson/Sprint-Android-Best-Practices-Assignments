package com.example.rxjavaassignment

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface RandomNumberService  {

    @GET("jsonI.php")
    fun getRandomNumbers(@Query("length") length: String, @Query("type") type: String): Single<RandomNumber>
}
