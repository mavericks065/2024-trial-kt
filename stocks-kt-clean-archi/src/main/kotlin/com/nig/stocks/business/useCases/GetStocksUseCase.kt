package com.nig.stocks.business.useCases

import com.nig.stocks.business.domain.PageSearch
import com.nig.stocks.business.domain.PaginatedResult
import com.nig.stocks.business.domain.Stock
import org.springframework.data.domain.Page
import java.sql.Date

interface GetStocksUseCase {
    fun getStocks(symbol: String?, fromDate: Date?, page: PageSearch): PaginatedResult<Stock>
}
