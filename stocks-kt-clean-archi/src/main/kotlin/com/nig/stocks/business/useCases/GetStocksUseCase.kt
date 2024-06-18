package com.nig.stocks.business.useCases

import com.nig.stocks.business.domain.Stock
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import java.sql.Date

interface GetStocksUseCase {
    fun getStocks(symbol: String?, fromDate: Date?, pageable: Pageable): Page<Stock>
}
