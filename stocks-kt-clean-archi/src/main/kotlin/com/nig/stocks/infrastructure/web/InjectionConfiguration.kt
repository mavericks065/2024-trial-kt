package com.nig.stocks.infrastructure.web

import com.nig.stocks.business.useCases.GetStocksUseCase
import com.nig.stocks.business.useCases.GetStocksUseCaseImpl
import com.nig.stocks.infrastructure.db.gateways.StockRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class InjectionConfiguration {
    @Bean
    fun getGetStocksUseCase(repository: StockRepository): GetStocksUseCase {
        return GetStocksUseCaseImpl(repository)
    }
}
