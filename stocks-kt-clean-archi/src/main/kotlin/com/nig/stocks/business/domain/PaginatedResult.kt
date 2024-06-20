package com.nig.stocks.business.domain

data class PaginatedResult<T>(val content: List<T>, val totalSize: Long) {
    fun <R> map(transform: (T) -> R): PaginatedResult<R> = PaginatedResult(content.map(transform), totalSize)
}
