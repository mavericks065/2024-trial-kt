package com.nig.stocks.business.useCases

import com.nig.stocks.business.domain.PageSearch
import com.nig.stocks.business.domain.PaginatedResult
import com.nig.stocks.business.domain.Stock
import com.nig.stocks.business.gateways.StocksGateway
import com.nig.stocks.infrastructure.db.model.StockModel
import org.springframework.data.domain.Page
import java.sql.Date
import kotlin.math.abs

class GetStocksUseCaseImpl(private val gateway: StocksGateway): GetStocksUseCase {

    override fun getStocks(symbol: String?, fromDate: Date?, page: PageSearch): PaginatedResult<Stock> {
        val stockFound = if (symbol == null && fromDate == null) {
            gateway.findAllStocks(page)
        } else {
            gateway.findStocksBy(
                symbol = symbol, date = fromDate, page = page
            )
        }

        return stockFound.map {
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
