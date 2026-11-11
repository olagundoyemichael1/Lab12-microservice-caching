// Lab 12
package com.example.repositories

import com.example.database.Tasks
import com.example.models.Task
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

class PostgresTaskRepository {
    fun findAll(): List<Task> = transaction {
        Tasks.selectAll().orderBy(Tasks.createdAt to SortOrder.DESC).map { it.toTask() }
    }
    fun findById(id: Int): Task? = transaction {
        Tasks.select { Tasks.id eq id }.map { it.toTask() }.singleOrNull()
    }
    fun create(title: String, description: String, priority: Int): Task = transaction {
        val stmt = Tasks.insert {
            it[Tasks.title] = title
            it[Tasks.description] = description
            it[Tasks.priority] = priority
        }
        Task(id = stmt[Tasks.id], title = title, description = description, priority = priority)
    }
    fun update(id: Int, title: String, description: String, priority: Int): Task? = transaction {
        val rows = Tasks.update({ Tasks.id eq id }) {
            it[Tasks.title] = title; it[Tasks.description] = description; it[Tasks.priority] = priority
        }
        if (rows > 0) findById(id) else null
    }
    fun toggleCompleted(id: Int): Task? = transaction {
        val current = Tasks.select { Tasks.id eq id }.singleOrNull() ?: return@transaction null
        val nv = !current[Tasks.completed]
        Tasks.update({ Tasks.id eq id }) { it[completed] = nv }
        findById(id)
    }
    fun delete(id: Int): Boolean = transaction { Tasks.deleteWhere { Tasks.id eq id } > 0 }
    fun findByCompleted(completed: Boolean): List<Task> = transaction { Tasks.select { Tasks.completed eq completed }.map { it.toTask() } }
    fun findByPriorityGreaterThan(priority: Int): List<Task> = transaction { Tasks.select { Tasks.priority greater priority }.map { it.toTask() } }
    private fun ResultRow.toTask() = Task(
        id = this[Tasks.id], title = this[Tasks.title], description = this[Tasks.description] ?: "",
        completed = this[Tasks.completed], priority = this[Tasks.priority], createdAt = this[Tasks.createdAt]
    )
}