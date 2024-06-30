package com.irissmile.minuteapplication.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.irissmile.minuteapplication.R


class LoginSampleActivity : AppCompatActivity() {

    private lateinit var snackbar: Snackbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_sample)

        snackbar = Snackbar.make(
            findViewById(android.R.id.content),
            "This is just a sample activity, it is not functional!",
            Snackbar.LENGTH_INDEFINITE)
            .setAction("OK") {
                snackbar.dismiss()
            }

        snackbar.show()
    }
}