package com.nig.stocks.infrastructure.web.controllers

import com.nig.stocks.infrastructure.web.model.StockAPI
import com.nig.stocks.useCases.GetStocksUseCase
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/internalApi/v1/stocks")
class StocksController(@Autowired val getStocksUseCase: GetStocksUseCase) {
    @GetMapping("")
    fun getStocks(@RequestParam symbol: String?): List<StockAPI> = getStocksUseCase.getStocks(symbol)
}
