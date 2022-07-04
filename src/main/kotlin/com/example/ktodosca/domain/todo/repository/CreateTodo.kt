package com.example.ktodosca.domain.todo.repository

import com.example.ktodosca.domain.todo.Todo

interface CreateTodo {
   suspend fun addTodo(todo: Todo): Todo
}
