package com.example.ktodosca.usecase.todo.mother

import com.example.ktodosca.domain.todo.Status
import com.example.ktodosca.domain.todo.Todo
import com.example.ktodosca.domain.todo.repository.GetTodos
import com.example.ktodosca.shared.mother.todos
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow


class FakeGeTodoRepository : GetTodos {

    private val todos: List<Todo> = todos()


    override suspend fun all(): Flow<Todo> = todos.asFlow()

    override suspend fun withStatus(status: Status): Flow<Todo> =
        todos.filter { it.status === status }.asFlow()
}
