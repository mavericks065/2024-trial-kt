package com.nig.stocks.infrastructure.persistence.repository

import jakarta.annotation.PostConstruct
import org.springframework.core.io.Resource
import org.springframework.core.io.ResourceLoader
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.io.BufferedReader
import java.io.InputStreamReader
import java.sql.Date
import java.util.*


@Service
class DatabaseLoader(private val jdbcTemplate: JdbcTemplate, private val resourceLoader: ResourceLoader) {
    @PostConstruct
    @Transactional
    @Throws(Exception::class)
    fun loadData() {
        val resource: Resource = resourceLoader.getResource("classpath:stocks.csv")
        val reader = BufferedReader(InputStreamReader(resource.getInputStream()))

        reader.lines().skip(1).map<Array<String>> { line: String ->
            line.split(",".toRegex())
                .dropLastWhile { it.isEmpty() }
                .toTypedArray()
        }
            .forEach { fields: Array<String> ->
                if (fields.size >= 8) {
                    jdbcTemplate.update(
                        "INSERT INTO stocks(id, date, symbol, open, high, low, close, adj_close, volume) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)",
                        UUID.randomUUID(),
                        Date.valueOf(fields[0]),
                        fields[1],
                        fields[2].toDouble(),
                        fields[3].toDouble(),
                        fields[4].toDouble(),
                        fields[5].toDouble(),
                        fields[6].toDouble(),
                        fields[7].toLong()
                    )
                }

            }
    }
}
