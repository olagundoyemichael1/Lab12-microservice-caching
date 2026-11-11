// Lab 12
package com.example.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.Contextual
import java.time.Instant

@Serializable
data class Task(
    val id: Int? = null,
    val title: String,
    val description: String = "",
    val completed: Boolean = false,
    val priority: Int = 3,
    @Contextual val createdAt: Instant? = null
)