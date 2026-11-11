// Lab 12, Step 2: Implement Caching
package com.example.cache

import com.example.models.Task
import com.github.benmanes.caffeine.cache.Cache
import com.github.benmanes.caffeine.cache.Caffeine
import java.util.concurrent.TimeUnit

class TaskCache {
    private val cache: Cache<Int, Task> = Caffeine.newBuilder()
        .expireAfterWrite(10, TimeUnit.MINUTES)
        .maximumSize(1000)
        .recordStats()
        .build()

    fun get(id: Int): Task? = cache.getIfPresent(id)
    fun put(id: Int, task: Task) { cache.put(id, task) }
    fun invalidate(id: Int) { cache.invalidate(id) }
    fun invalidateAll() { cache.invalidateAll() }
    fun getStats(): Map<String, Any> = mapOf(
        "hitCount" to cache.stats().hitCount(),
        "missCount" to cache.stats().missCount(),
        "hitRate" to cache.stats().hitRate(),
        "size" to cache.estimatedSize()
    )
}