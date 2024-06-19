package com.nig.stocks.infrastructure.db.repositories

import com.nig.stocks.business.gateways.StocksGateway
import com.nig.stocks.infrastructure.db.jpaRepositories.StockJpaRepository
import com.nig.stocks.infrastructure.db.model.StockModel
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import java.sql.Date

class StocksRepository(private val repository: StockJpaRepository): StocksGateway {
    override fun findStocksBy(symbol: String?, date: Date?, pageable: Pageable): Page<StockModel> {
        return repository.findBySymbolOrDateGreaterThanEqual(symbol, date, pageable)
    }

    override fun findAllStocks(pageable: Pageable): Page<StockModel> {
        return repository.findAll(pageable)
    }

}
