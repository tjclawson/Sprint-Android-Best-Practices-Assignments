package com.example.crashlyticsassignment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.crashlytics.android.Crashlytics
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        val list = listOf("One", "Two", "Three")
    }

    lateinit var nullProperty: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button_crash_back_press.setOnClickListener {
            hanselAndGrettel("MainActivity", "BackPress Click Listener")
            onBackPressed()
        }

        button_crash_index.setOnClickListener {
            hanselAndGrettel("MainActivity", "Index out of Bounds Listener")
            button_crash_index.text = list[3]
        }

        button_crash_npe.setOnClickListener {
            hanselAndGrettel("MainActivity", "NPE Listener")
            button_crash_npe.text = nullProperty
        }

        button_crash_recurse.setOnClickListener {
            hanselAndGrettel("MainActivity", "Recurse Listener")
            button_crash_recurse.text = recurseCrash()
        }
    }

    override fun onBackPressed() {
        this.onBackPressed()
    }

    private fun recurseCrash(): String {
        return recurseCrash()
    }

    private fun hanselAndGrettel(currentClass: String, currentMethod: String) {
        val breadcrumb = "$currentClass $currentMethod"
        Crashlytics.log(breadcrumb)
    }
}
