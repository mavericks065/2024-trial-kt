package com.nig.stocks.infrastructure.persistence.model

import jakarta.persistence.*
import java.sql.Timestamp
import java.util.*

@Entity
@Table(name = "stocks")
data class StockModel(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: UUID,

    val date: Timestamp,
    val symbol: String,
    val open: Double,
    val high: Double,
    val low: Double,
    val close: Double,
    val adjClose: Double,
    val volume: Long
)
