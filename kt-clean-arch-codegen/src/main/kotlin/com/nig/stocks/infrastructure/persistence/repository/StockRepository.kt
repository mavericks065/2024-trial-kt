package com.nig.stocks.infrastructure.persistence.repository

import com.nig.stocks.infrastructure.persistence.model.StockModel
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.sql.Date
import java.util.UUID

@Repository
interface StockRepository : JpaRepository<StockModel, UUID> {
    fun findBySymbolOrDateGreaterThanEqual(symbol: String?, date: Date?, pageable: Pageable): Page<StockModel>
}
