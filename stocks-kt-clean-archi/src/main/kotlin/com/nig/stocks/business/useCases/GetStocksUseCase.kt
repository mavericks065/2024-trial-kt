package com.nig.stocks.business.useCases

import com.nig.stocks.business.domain.Stock
import com.nig.stocks.infrastructure.persistence.model.StockModel
import com.nig.stocks.infrastructure.persistence.repository.StockRepository
import com.nig.stocks.infrastructure.web.model.DayMetricAPI
import com.nig.stocks.infrastructure.web.model.StockAPI
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.sql.Date
import java.sql.Timestamp
import java.util.*
import kotlin.math.abs

@Service
class GetStocksUseCase(@Autowired val repository: StockRepository) {
    fun getStocks(symbol: String?, fromDate: Date?, pageable: Pageable): Page<Stock> {
        val stockFound = if (symbol == null && fromDate == null) {
            repository.findAll(pageable)
        } else {
            repository.findBySymbolOrDateGreaterThanEqual(
                symbol = symbol, date = fromDate, pageable = pageable
            )
        }

        return stockFound.map {it ->
            Stock(
                id = it.id,
                date = it.date.toInstant(),
                symbol = it.symbol,
                volume = it.volume,
                spread = computeDailySpread(it),
                variation = computeVariation(it),
                openingPrice = it.open,
                closingPrice = it.close,
                high = it.high,
                low = it.low,
                adjClosePrice =  it.close
            )
        }
    }

    private fun computeDailySpread(it: StockModel) = abs(it.close - it.open)

    private fun computeVariation(it: StockModel) = (it.high - it.low)
}
