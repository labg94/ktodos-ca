package com.example.ktodosca.usecase.todo

import com.example.ktodosca.domain.todo.Status
import com.example.ktodosca.domain.todo.Todo
import com.example.ktodosca.domain.todo.repository.GetTodos
import kotlinx.coroutines.flow.Flow

class TodoFetcherService(private val repository: GetTodos) : TodoFetcher {
    override suspend fun all(): Flow<Todo> = repository.all()

    override suspend fun byStatus(status: Status): Flow<Todo> = repository.withStatus(status)
}
