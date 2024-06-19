package com.nig.stocks.infrastructure.db.jpaRepositories

import com.nig.stocks.infrastructure.db.model.StockModel
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.sql.Date
import java.util.*

@Repository
interface StockJpaRepository : JpaRepository<StockModel, UUID> {
    fun findBySymbolOrDateGreaterThanEqual(symbol: String?, date: Date?, pageable: Pageable): Page<StockModel>
}
