package com.irissmile.minuteapplication.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.irissmile.minuteapplication.R

class MainActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.apiCategoryButton).setOnClickListener {
            startActivity(Intent(this, CategoriesActivity::class.java))
        }
        findViewById<Button>(R.id.dateConverterButton).setOnClickListener {
            startActivity(Intent(this, DateConverterActivity::class.java))
        }
    }


}