// Lab 12, Step 3: Create Cached Service
package com.example.services

import com.example.cache.TaskCache
import com.example.models.*
import com.example.repositories.PostgresTaskRepository

class CachedTaskService(private val repository: PostgresTaskRepository, private val cache: TaskCache) {
    fun getTask(id: Int): ServiceResult<Task> {
        val cached = cache.get(id)
        if (cached != null) return ServiceResult.Success(cached)
        val task = repository.findById(id)
        return if (task != null) { cache.put(id, task); ServiceResult.Success(task) } else ServiceResult.NotFound("Task with id $id not found")
    }
    fun createTask(request: CreateTaskRequest): ServiceResult<Task> {
        val task = repository.create(request.title, request.description, request.priority)
        cache.put(task.id!!, task)
        return ServiceResult.Success(task)
    }
    fun updateTask(id: Int, request: CreateTaskRequest): ServiceResult<Task> {
        val updated = repository.update(id, request.title, request.description, request.priority)
        return if (updated != null) { cache.put(id, updated); ServiceResult.Success(updated) } else ServiceResult.NotFound("Task with id $id not found")
    }
    fun deleteTask(id: Int): ServiceResult<Unit> =
        if (repository.delete(id)) { cache.invalidate(id); ServiceResult.Success(Unit) } else ServiceResult.NotFound("Task with id $id not found")
    fun getAllTasks(): ServiceResult<List<Task>> = ServiceResult.Success(repository.findAll())
    fun getCacheStats(): Map<String, Any> = cache.getStats()
}