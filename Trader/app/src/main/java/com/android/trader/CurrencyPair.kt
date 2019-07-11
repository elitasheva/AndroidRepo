package com.android.trader

data class Product(
    val name: String,
    val sellPrice: Double,
    val buyPrice: Double,
    val percentage: Double,
    val isCurrencyPair: Boolean
)