package com.nig.stocks.infrastructure.repository

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import org.springframework.stereotype.Repository

import jakarta.persistence.*
import org.springframework.data.repository.CrudRepository
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

@Repository
interface StockRepository: CrudRepository<StockModel, UUID> {
    fun findBySymbol(symbol: String): List<StockModel>
}
