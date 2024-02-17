package com.gateway.api

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class GatewayApplication

fun main(args: Array<String>) {
	runApplication<GatewayApplication>(*args)
}
