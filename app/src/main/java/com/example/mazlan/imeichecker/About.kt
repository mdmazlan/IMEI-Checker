package com.example.mazlan.imeichecker

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button

class About : AppCompatActivity() {

    internal lateinit var facebook: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)


        facebook = findViewById<Button>(R.id.btn_facebook)


        facebook.setOnClickListener {
            val i = openFacebook (this@About)
            startActivity(i)
        }
    }

    fun openFacebook(context: Context): Intent {

        try {
            context.packageManager
                    .getPackageInfo("com.example.mazlan.allresultbd", 0)

            return Intent(Intent.ACTION_VIEW,

                    Uri.parse("https://www.facebook.com/mdmazlan01"))

        } catch
        (e: Exception) {

            return Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://www.facebook.com/mdmazlan01"))
        }
    }
}