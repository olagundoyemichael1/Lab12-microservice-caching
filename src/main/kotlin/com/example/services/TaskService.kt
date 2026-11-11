// Lab 12
package com.example.services

import com.example.models.CreateTaskRequest
import com.example.models.Task
import com.example.repositories.PostgresTaskRepository

class TaskService(private val repository: PostgresTaskRepository) {
    fun getAllTasks(): List<Task> = repository.findAll()
    fun getTask(id: Int): Task? = repository.findById(id)
    fun createTask(request: CreateTaskRequest): Task = repository.create(request.title, request.description, request.priority)
    fun updateTask(id: Int, request: CreateTaskRequest): Task? = repository.update(id, request.title, request.description, request.priority)
    fun toggleTask(id: Int): Task? = repository.toggleCompleted(id)
    fun deleteTask(id: Int): Boolean = repository.delete(id)
    fun getCompletedTasks(): List<Task> = repository.findByCompleted(true)
    fun getPendingTasks(): List<Task> = repository.findByCompleted(false)
    fun getHighPriorityTasks(): List<Task> = repository.findByPriorityGreaterThan(3)
}