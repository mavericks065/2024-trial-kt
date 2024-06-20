package com.nig.stocks.infrastructure.web.controllers

import com.nig.stocks.business.domain.PageSearch
import com.nig.stocks.business.domain.PaginatedResult
import com.nig.stocks.business.useCases.GetStocksUseCase
import com.nig.stocks.infrastructure.web.model.DayMetricAPI
import com.nig.stocks.infrastructure.web.model.StockAPI
import io.swagger.v3.oas.annotations.Operation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.sql.Date

@RestController
@RequestMapping("/internalApi/v1/stocks")
class StocksController(@Autowired val getStocksUseCase: GetStocksUseCase) {

    @GetMapping("")
    @Operation(summary = "Get stocks", description = "Get all stocks or filter by symbol or fromDate with paginated result")
    fun getStocks(
        @RequestParam symbol: String?,
        @RequestParam fromDate: Date?,
        @RequestParam(required = false, defaultValue = "0") page: Int,
        @RequestParam(required = false, defaultValue = "5") size: Int
    ): PaginatedResult<StockAPI> {
        val stocks = getStocksUseCase.getStocks(symbol, fromDate, PageSearch(page, size))
        return stocks.map {
            StockAPI(
                it.symbol,
                it.date,
                DayMetricAPI(
                    volume = it.volume,
                    variation = it.variation,
                    spread = it.spread,
                    closingPrice = it.closingPrice
                )
            )
        }
    }
}
