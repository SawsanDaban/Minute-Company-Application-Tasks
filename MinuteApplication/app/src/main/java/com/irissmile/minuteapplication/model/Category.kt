package com.irissmile.minuteapplication.model

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
