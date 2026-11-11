// Lab 12
package com.example.models

import kotlinx.serialization.Serializable

@Serializable
data class CreateTaskRequest(val title: String, val description: String = "", val priority: Int = 3)