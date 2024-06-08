package com.nig.stocks.useCases

import com.nig.stocks.infrastructure.repository.StockModel
import com.nig.stocks.infrastructure.repository.StockRepository
import com.nig.stocks.infrastructure.web.model.LastDayMetricAPI
import com.nig.stocks.infrastructure.web.model.StockAPI
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import kotlin.math.abs

@Service
class GetStocksUseCase(@Autowired val repository: StockRepository) {

    fun getStocks(symbol: String?): List<StockAPI> {
        val stockStored = symbol?.let { repository.findBySymbol(symbol) } ?: run {
            repository.findAll()
        }

        return stockStored.map { it ->
            StockAPI(
                id = it.id,
                it.symbol,
                LastDayMetricAPI(
                    volume = it.volume,
                    variation = computeVariation(it),
                    spread = computeDailySpread(it),
                    closingPrice = it.close
                )
            )
        }
    }

    private fun computeDailySpread(it: StockModel) = abs(it.close - it.open)

    private fun computeVariation(it: StockModel) = (it.high - it.low)
}
