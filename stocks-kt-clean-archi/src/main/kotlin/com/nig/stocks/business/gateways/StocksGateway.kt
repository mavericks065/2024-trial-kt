package com.nig.stocks.business.gateways

import com.nig.stocks.business.domain.PageSearch
import com.nig.stocks.business.domain.PaginatedResult
import com.nig.stocks.infrastructure.db.model.StockModel
import org.springframework.data.domain.Page
import java.sql.Date

interface StocksGateway {
    fun findStocksBy(symbol: String?, date: Date?, page: PageSearch): PaginatedResult<StockModel>
    fun findAllStocks(page: PageSearch): PaginatedResult<StockModel>
}
