package com.lambdaschool.basicandroidnetworking.retrofit

import android.app.Application
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.lambdaschool.basicandroidnetworking.App
import com.lambdaschool.basicandroidnetworking.R
import com.lambdaschool.basicandroidnetworking.RetrofitComponent
import com.lambdaschool.basicandroidnetworking.model.AdviceMsg
import kotlinx.android.synthetic.main.activity_retrofit.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import javax.inject.Inject

class RetrofitActivity : AppCompatActivity(), Callback<AdviceMsg> {

    companion object {
        private const val TAG = "RETROFIT"
    }

    @Inject
    lateinit var adviceDagger: AdviceAPI

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_retrofit)

        //retrofitComponent =
        (application as App).retrofitComponent.inject(this)

        fetchNetworkAPIRetrofitButton.setOnClickListener {
           adviceDagger.randomAdvice().enqueue(this)
        }

        fetchNetworkAPIOkHttpButton.setOnClickListener {
            AdviceRetriever().getRandomAdviceWithOkHttp().enqueue(this)
        }
    }

    // Callback for Retrofit Call
    override fun onResponse(call: Call<AdviceMsg>, response: Response<AdviceMsg>) {
        Log.d("RETROFIT", "onResponse")
        if (response.isSuccessful) {
            val adviceMsg = response.body()
            Log.d(TAG, adviceMsg?.getAdvice())
            adviceTextRetrofit.text = adviceMsg?.getAdvice()
        } else {
            val response = "response not successful; ${response.errorBody().toString()}"
            Log.d(TAG, response)
            Toast.makeText(this@RetrofitActivity, response, Toast.LENGTH_SHORT).show()
        }
    }

    // Callback for Retrofit Call
    override fun onFailure(call: Call<AdviceMsg>, t: Throwable) {
        t.printStackTrace()
        val response = "faliure; ${t.printStackTrace()}"
        Log.d(TAG, response)
        Toast.makeText(this@RetrofitActivity, response, Toast.LENGTH_SHORT).show()
    }
}
