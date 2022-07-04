package com.example.ktodosca.usecase.todo

import com.example.ktodosca.domain.todo.Todo

interface NewTaskAdded {
   suspend fun createTodo(task: String): Todo
}
