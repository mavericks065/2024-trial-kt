package com.nig.stocks.application.codegen

import org.junit.jupiter.api.Test
import org.springframework.http.ResponseEntity

class StocksApiTest {

    private val api: StocksApiController = StocksApiController()

    /**
     * To test StocksApiController.getStocks
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    fun getStocksTest() {
        val size: java.math.BigDecimal = TODO()
        val response: ResponseEntity<kotlin.Any> = api.getStocks(size)

        // TODO: test validations
    }
}
