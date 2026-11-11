// Lab 12, Step 7: Wire Everything Together
package com.example

import com.example.cache.TaskCache
import com.example.database.DatabaseFactory
import com.example.plugins.*
import com.example.repositories.PostgresTaskRepository
import com.example.routes.taskRoutes
import com.example.services.CachedTaskService
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.routing.*

fun main() {
    DatabaseFactory.init()

    val repository = PostgresTaskRepository()
    val cache = TaskCache()
    val service = CachedTaskService(repository, cache)

    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
        configureLogging()
        configureErrorHandling()
        install(ContentNegotiation) { json() }

        routing {
            healthRoutes()
            taskRoutes(service)
        }
    }.start(wait = true)
}