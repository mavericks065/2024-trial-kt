package com.nig.stocks.infrastructure.web.controllers

import com.nig.stocks.infrastructure.web.model.StockAPI
import com.nig.stocks.business.useCases.GetStocksUseCase
import com.nig.stocks.infrastructure.web.model.DayMetricAPI
import io.swagger.v3.oas.annotations.Operation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
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
        @PageableDefault(size = 5) pageable: Pageable
    ): Page<StockAPI> {
        val stocks = getStocksUseCase.getStocks(symbol, fromDate, pageable)
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
