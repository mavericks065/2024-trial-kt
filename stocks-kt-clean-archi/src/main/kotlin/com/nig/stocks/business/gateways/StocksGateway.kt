package com.nig.stocks.business.gateways

import com.nig.stocks.infrastructure.db.model.StockModel
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import java.sql.Date

interface StocksGateway {
    fun findStocksBy(symbol: String?, date: Date?, pageable: Pageable): Page<StockModel>
    fun findAllStocks(pageable: Pageable): Page<StockModel>
}
