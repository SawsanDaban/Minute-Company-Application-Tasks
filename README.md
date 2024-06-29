# Android-Application-Tasks

## Task 1 : Integrate API using RetrofitApi and display the response in a table form

  * API EndPoint : https://apis.minute.com.sa/api/controlPanel/getCategory

  * Examples for each of the following :
    * HTTP verbs
      * GET
      * POST

  * In the HTTP context, the main difference between GET and POST :
  
|               | GET           | POST          |
| ------------- | ------------- | ------------- |
| Parameters, Values        | Visible in URL | Not visible in URL |
| Cache         | Can be cached | Not cacheable |
| Security      | Not secure, the data are saved in the browser history | Secured, the data is not saved in the web browser  |

* To start using the Retrofit library to fetch the data from the URL we add the following dependencies:
  ```
   implementation("com.squareup.retrofit2:retrofit:2.1.0")
   implementation("com.squareup.retrofit2:converter-gson:2.1.0")
  ```
* Next we create data classes:
  
    * Data class for the response
      ```
      data class CategoryResponse(
        val messageEn: String,
        val messageAr: String,
        val status: Boolean,
        val data: List<Category>
      )
      ```
  
     * Data class for the Category information
       
        ```
        data class Category(
          val id: Int,
          val nameEn: String,
          val nameAr: String,
          val subtitleEn: String,
          val subtitleAr: String,
          val image: String?,
          val basicPrice: Float,
          val timeMinutePrice: Float,
          val minPrice: Float,
          val governmentFees: Float,
          val priceForKilo: Float,
          val createdAt: String?,
          val updatedAt: String
        )
        ```
        
* Next we create an interface for the URL endpoint
    ```
    interface MinuteAPI {
      @GET("getCategory")
      fun getCategories(): Call<CategoryResponse>
    }
    ```

* Next we implement the Retrofit configuration
    ```
    val api = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MinuteAPI::class.java)
    ```

* Now we fetch the data from the API endpoint
    ```
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
    ```

* To display the data in a table we implement the following method
    ```
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
    ```

* To sort the data using the category ID or basic price we implement the following methods
    ```
    private fun sortTableById() {
      categories = categories.sortedBy { it.id }
      populateTable(categories)
    }
      
    private fun sortTableByBasicPrice() {
      categories = categories.sortedBy { it.basicPrice }
      populateTable(categories)
    }
    ```
    
* Screenshot of the output
  
  ![studio64_f38mlR0q7m](https://user-images.githubusercontent.com/33127540/196431761-ea5631a1-331a-42c2-af59-6588b2a39776.png)

---------------------

## Task 2 : Convert the Gregorian date to Hijiri date 

* I used `Calender` and `IslamicCalender` libraries privided by Java.
* First I extended the `DatePickerDialog.OnDateSetListener` class, to use the date picker material.
* Then I implemented the following code to get the date from the user
  ```
  val cal = Calendar.getInstance()
  val year = cal.get(Calendar.YEAR)
  val month = cal.get(Calendar.MONTH)
  val day = cal.get(Calendar.DAY_OF_MONTH)

  findViewById<Button>(R.id.pickDate).setOnClickListener {
      DatePickerDialog(this, this, year, month, day).show()
  }
  ```
* The `OnDateSet` method is called when the user picks a date, so when the user picks a date we call the methed `ConvertGregorianToHijri`
  ```
  override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        ConvertGregorianToHijri(year, month, dayOfMonth)
  }
  ```
* The implementation for the `ConvertGregorianToHijri` method
  ```
  private fun ConvertGregorianToHijri(year: Int, month: Int, dayOfMonth: Int) {
        val gregorianCalendar = Calendar.getInstance()
        gregorianCalendar.set(year, month, dayOfMonth)

        findViewById<TextView>(R.id.georgoanText).text = "Picked Date: " + gregorianFormatter.format(gregorianCalendar.timeInMillis)
        Log.i("GregorianCalendar", gregorianFormatter.format(gregorianCalendar.timeInMillis))

        val islamicCalendar = IslamicCalendar()
        islamicCalendar.time = gregorianCalendar.time

        val hijriYear = islamicCalendar.get(IslamicCalendar.YEAR)
        val hijriMonth = islamicCalendar.get(IslamicCalendar.MONTH) // Months are 0-based
        val hijriDay = islamicCalendar.get(IslamicCalendar.DAY_OF_MONTH)

        val monthName = getHijriMonthName(hijriMonth)

        findViewById<TextView>(R.id.hijriText).text = "Converted Date: " + String.format(Locale.US, "%s %02d, %d", monthName, hijriDay, hijriYear)
        Log.i("IslamicCalendar", String.format("%d-%02d-%02d", hijriYear, hijriMonth, hijriDay))
  }
  ```
* Screenshot of the output
  
  ![studio64_f38mlR0q7m](https://user-images.githubusercontent.com/33127540/196431761-ea5631a1-331a-42c2-af59-6588b2a39776.png)
  
---------------------

## Task 3 : Design a UI

  
  
---------------------

* Check the application source code [HERE]().
