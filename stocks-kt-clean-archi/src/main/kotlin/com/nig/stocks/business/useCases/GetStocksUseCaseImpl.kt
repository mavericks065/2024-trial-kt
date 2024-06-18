package com.nig.stocks.business.useCases

import com.nig.stocks.business.domain.Stock
import com.nig.stocks.infrastructure.db.gateways.StockRepository
import com.nig.stocks.infrastructure.db.model.StockModel
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import java.sql.Date
import kotlin.math.abs

class GetStocksUseCaseImpl(private val repository: StockRepository): GetStocksUseCase {

    override fun getStocks(symbol: String?, fromDate: Date?, pageable: Pageable): Page<Stock> {
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
