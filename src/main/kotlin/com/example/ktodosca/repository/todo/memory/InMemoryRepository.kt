package com.example.ktodosca.repository.todo.memory

import com.example.ktodosca.domain.todo.Status
import com.example.ktodosca.domain.todo.Todo
import com.example.ktodosca.domain.todo.TodoId
import com.example.ktodosca.domain.todo.repository.CreateTodo
import com.example.ktodosca.domain.todo.repository.GetTodos
import com.example.ktodosca.domain.todo.repository.UpdateTodo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Repository

@Repository
@Profile("memory")
class InMemoryRepository : CreateTodo, GetTodos, UpdateTodo {
    private val todos: MutableList<Todo> = mutableListOf()


    override suspend fun addTodo(todo: Todo): Todo = todo.apply { todos.add(this) }

    override suspend fun all(): Flow<Todo> = todos.asFlow()

    override suspend fun withStatus(status: Status): Flow<Todo> = todos.filter { it.status === status }.asFlow()

    override suspend fun existsById(id: TodoId): Boolean = todos.find { it.id == id } !== null
    override suspend fun findById(id: TodoId): Todo? = todos.find { it.id == id }

    override suspend fun update(todo: Todo): Todo = addTodo(todo)

    override suspend fun remove(id: TodoId): Boolean = todos.removeIf { it.id == id }


    fun deleteAll() {

        todos.removeAll { true }

    }
}
