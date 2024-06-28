package com.irissmile.minuteapplication.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.ViewGroup
import android.widget.Button
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import com.irissmile.minuteapplication.interfaces.MinuteAPI
import com.irissmile.minuteapplication.R
import com.irissmile.minuteapplication.model.Category
import com.irissmile.minuteapplication.model.CategoryResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CategoriesActivity : AppCompatActivity() {

    private val BASE_URL = "https://apis.minute.com.sa/api/controlPanel/"
    private val TAG = "CATEGORIES_RESPONSE"
    private lateinit var tableLayout: TableLayout
    private lateinit var sortButton: Button
    private lateinit var sortBasicPriceButton: Button
    private lateinit var categories: List<Category>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_categories)

        tableLayout = findViewById(R.id.tableLayout)
        sortButton = findViewById(R.id.sortButton)
        sortButton.setOnClickListener {
            sortTableById()
        }
        sortBasicPriceButton = findViewById(R.id.sortBasicPriceButton)
        sortBasicPriceButton.setOnClickListener {
            sortTableByBasicPrice()
        }
        getAllCategories()
    }

    private fun getAllCategories(){
        val api = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MinuteAPI::class.java)

        api.getCategories().enqueue(object : Callback<CategoryResponse> {

            override fun onResponse(call: Call<CategoryResponse>, response: Response<CategoryResponse>) {
                if (response.isSuccessful) {
                    val categoryResponse = response.body()
                    if (categoryResponse != null) {
                        if (categoryResponse.status) {
                            categories = categoryResponse.data
                            populateTable(categoryResponse.data)
                            for (category in categoryResponse.data) {
                                Log.i(TAG, "onResponse: ${category.toString()}")
                            }
                        } else {
                            Log.i(TAG, "onResponse: Failed to fetch categories: ${categoryResponse.messageEn}")
                        }
                    } else {
                        Log.i(TAG, "onResponse: Response body is null")
                    }
                } else {
                    Log.i(TAG, "onResponse: Response not successful")
                }
            }

            override fun onFailure(call: Call<CategoryResponse>, t: Throwable) {
                Log.i(TAG, "onFailure: ${t.message}")
            }
        })
    }

    private fun populateTable(categories: List<Category>) {
        tableLayout.removeViews(1, tableLayout.childCount - 1) // Clear existing rows except the header
        for (category in categories) {
            val tableRow = TableRow(this)
            tableRow.layoutParams = TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT
            )

            val idTextView = createTextView(category.id.toString())
            val nameTextView = createTextView(category.nameEn)
            val basicPriceTextView = createTextView(category.basicPrice.toString())

            tableRow.addView(idTextView)
            tableRow.addView(nameTextView)
            tableRow.addView(basicPriceTextView)

            tableLayout.addView(tableRow)
        }
    }

    private fun createTextView(text: String): TextView {
        val textView = TextView(this)
        textView.text = text
        textView.layoutParams = TableRow.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        textView.setPadding(8, 8, 8, 8)
        textView.gravity = Gravity.CENTER
        return textView
    }

    private fun sortTableById() {
        categories = categories.sortedBy { it.id }
        populateTable(categories)
    }

    private fun sortTableByBasicPrice() {
        categories = categories.sortedBy { it.basicPrice }
        populateTable(categories)
    }
}