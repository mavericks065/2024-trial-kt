package com.nig.stocks.infrastructure.web.model

import java.time.Instant

data class StockAPI(val symbol: String, val date: Instant, val dayMetric: DayMetricAPI)

data class DayMetricAPI(
    val volume: Long,
    val variation: Double, // difference between highest and lowest price
    val spread: Double, // difference between opening and closing price
    val closingPrice: Double
)

