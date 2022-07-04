package com.example.ktodosca.repository.todo.mongo

import com.example.ktodosca.domain.todo.Status
import com.example.ktodosca.domain.todo.Todo
import com.example.ktodosca.domain.todo.TodoId
import com.example.ktodosca.domain.todo.repository.CreateTodo
import com.example.ktodosca.domain.todo.repository.GetTodos
import com.example.ktodosca.domain.todo.repository.UpdateTodo
import com.example.ktodosca.repository.todo.mongo.dao.TodoMongoDao
import com.example.ktodosca.repository.todo.mongo.mapper.toDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.reactive.asFlow
import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.stereotype.Repository

@Repository
class TodoMongoRepository(private val dao: TodoMongoDao) : CreateTodo, GetTodos, UpdateTodo {
    override suspend fun addTodo(todo: Todo): Todo = dao.save(todo.toDto()).awaitSingle().toDomain()

    override suspend fun all(): Flow<Todo> = dao.findAll().map { it.toDomain() }.asFlow()

    override suspend fun withStatus(status: Status): Flow<Todo> =
        dao.findAllByStatus(status.toString()).map { it.toDomain() }.asFlow()

    override suspend fun existsById(id: TodoId): Boolean = dao.existsById(id.value).awaitSingle()

    override suspend fun update(todo: Todo): Todo = addTodo(todo)

    override suspend fun remove(id: TodoId): Boolean =
        try {
            dao.deleteById(id.value).awaitSingleOrNull()
            true
        } catch (e: Exception) {
            false
        }
}
