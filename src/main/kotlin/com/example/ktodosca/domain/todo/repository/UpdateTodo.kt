package com.example.ktodosca.domain.todo.repository

import com.example.ktodosca.domain.todo.Todo
import com.example.ktodosca.domain.todo.TodoId

interface UpdateTodo {
  suspend  fun existsById(id: TodoId): Boolean
  suspend  fun update(todo: Todo): Todo
  suspend  fun remove(id: TodoId) : Boolean
}
