package com.irissmile.minuteapplication.model

data class CategoryResponse(
    val messageEn: String,
    val messageAr: String,
    val status: Boolean,
    val data: List<Category>
)
