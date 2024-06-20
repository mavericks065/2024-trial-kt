package com.nig.stocks.infrastructure.db.repositories

import com.nig.stocks.business.domain.PageSearch
import com.nig.stocks.business.domain.PaginatedResult
import com.nig.stocks.business.gateways.StocksGateway
import com.nig.stocks.infrastructure.db.jpaRepositories.StockJpaRepository
import com.nig.stocks.infrastructure.db.model.StockModel
import com.nig.stocks.infrastructure.db.toPaginatedResult
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import java.sql.Date

class StocksRepository(private val repository: StockJpaRepository) : StocksGateway {
    override fun findStocksBy(symbol: String?, date: Date?, page: PageSearch): PaginatedResult<StockModel> {
        return repository.findBySymbolOrDateGreaterThanEqual(
            symbol,
            date,
            PageRequest.of(page.pageNumber, page.pageSize)
        ).toPaginatedResult()
    }

    override fun findAllStocks(page: PageSearch): PaginatedResult<StockModel> {
        return repository.findAll(PageRequest.of(page.pageNumber, page.pageSize))
            .toPaginatedResult()
    }

}
