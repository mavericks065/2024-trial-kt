package com.nig.stocks.infrastructure.web

import com.nig.stocks.business.gateways.StocksGateway
import com.nig.stocks.business.useCases.GetStocksUseCase
import com.nig.stocks.business.useCases.GetStocksUseCaseImpl
import com.nig.stocks.infrastructure.db.jpaRepositories.StockJpaRepository
import com.nig.stocks.infrastructure.db.repositories.StocksRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class InjectionConfiguration {
    @Bean
    fun getGetStocksUseCase(gateway: StocksGateway): GetStocksUseCase {
        return GetStocksUseCaseImpl(gateway)
    }

    @Bean
    fun getStocksGateway(repository: StockJpaRepository): StocksGateway {
        return StocksRepository(repository)
    }
}
