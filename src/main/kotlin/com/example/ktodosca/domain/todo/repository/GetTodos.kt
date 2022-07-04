package com.example.ktodosca.domain.todo.repository

import com.example.ktodosca.domain.todo.Status
import com.example.ktodosca.domain.todo.Todo
import kotlinx.coroutines.flow.Flow

interface GetTodos {

    suspend fun all(): Flow<Todo>

    suspend fun withStatus(status: Status): Flow<Todo>

}
