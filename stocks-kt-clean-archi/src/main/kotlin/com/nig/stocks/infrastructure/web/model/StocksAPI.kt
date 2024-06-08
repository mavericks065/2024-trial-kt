package com.nig.stocks.infrastructure.web.model

import java.util.*

data class StockAPI(val id: UUID, val symbol: String, val lastDay: LastDayMetricAPI)

data class LastDayMetricAPI(
    val volume: Long,
    val variation: Double, // difference between highest and lowest price
    val spread: Double, // difference between opening and closing price
    val closingPrice: Double
)

