package com.example.ktodosca.usecase.todo

import com.example.ktodosca.domain.todo.Status
import com.example.ktodosca.domain.todo.Todo
import kotlinx.coroutines.flow.Flow

interface TodoFetcher {
    suspend fun all(): Flow<Todo>
    suspend fun byStatus(status: Status): Flow<Todo>
}
