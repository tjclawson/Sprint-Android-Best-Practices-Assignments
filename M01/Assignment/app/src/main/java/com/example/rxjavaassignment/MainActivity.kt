package com.example.rxjavaassignment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.jakewharton.rxbinding3.widget.textChanges
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.Observables
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.math.pow

class MainActivity : AppCompatActivity() {

    lateinit var disposable: Disposable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val obsPrice = et_purchase_price.textChanges().filter { it.length > 1 }
        val obsDownPayment = et_down_payment.textChanges().filter { it.length > 1 }
        val obsRate = et_interest_rate.textChanges().filter { it.length > 1 }
        val obsLength = et_loan_length.textChanges().filter { it.length > 1 }

        val obsCombined = Observables.combineLatest(obsPrice, obsDownPayment, obsRate, obsLength) { price, downPayment, rate, length ->
            calculatePayment(price, downPayment, rate, length)
        }

        //disposable = obsCombined.observeOn(AndroidSchedulers.mainThread()).subscribe { payment -> tv_mortgage_payment.text = payment}

        val retrofit = Retrofit.Builder().baseUrl("http://qrng.anu.edu.au/API/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

        val service = retrofit.create(RandomNumberService::class.java)

        disposable = service.getRandomNumbers("2", "unit8").subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe ({ data -> Log.i("BIGBRAIN", data.toString()) },
                { data -> Log.i("BIGBRAIN", data.message)})


    }

    private fun calculatePayment(textprice: CharSequence, textdownPayment: CharSequence, textannualRate: CharSequence, textlength: CharSequence): String {
        val price = textprice.toString().toDouble()
        val downPayment = textdownPayment.toString().toDouble()
        val annualRate = textannualRate.toString().toDouble()
        val length = textlength.toString().toDouble()
        val monthlyRate = annualRate / 12.0
        val formulaRate = monthlyRate + 1.0

        return ((((price - downPayment) * (formulaRate).pow(length)) / (formulaRate.pow(length - 1.0) )) / (12.0 * length)).toString()
    }
}
