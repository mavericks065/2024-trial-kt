package com.nig.stocks.infrastructure.web.controllers

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

data class HelloWorld(val message: String)

@RestController
class HealthCheckController {
    @GetMapping("/health")
    fun healthCheck() = HelloWorld("Hello World the health check is working fine.")
}
