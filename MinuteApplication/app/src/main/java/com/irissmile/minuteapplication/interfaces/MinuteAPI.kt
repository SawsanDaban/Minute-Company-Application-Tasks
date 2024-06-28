package com.irissmile.minuteapplication.interfaces

import com.irissmile.minuteapplication.model.CategoryResponse
import retrofit2.Call;
import retrofit2.http.GET;

interface MinuteAPI {
    @GET("getCategory")
    fun getCategories(): Call<CategoryResponse>
}