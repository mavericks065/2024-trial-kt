package com.nig.stocks.business.domain

import java.time.Instant
import java.util.*


data class Stock(
    val id: UUID,
    val date: Instant,
    val symbol: String,
    val volume: Long,
    val variation: Double, // difference between highest and lowest price
    val spread: Double, // difference between opening and closing price
    val high: Double,
    val low: Double,
    val openingPrice: Double,
    val closingPrice: Double,
    val adjClosePrice: Double,
)
