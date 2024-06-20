package com.nig.stocks.infrastructure.db

import com.nig.stocks.business.domain.PaginatedResult
import org.springframework.data.domain.Page

fun <T> Page<T>.toPaginatedResult(): PaginatedResult<T> = PaginatedResult(content, totalElements)
