package com.example.ktodosca.usecase.todo

import com.example.ktodosca.domain.todo.Todo
import com.example.ktodosca.domain.todo.TodoId

interface StatusChange {
  suspend  fun nextStatus(id: TodoId): Todo
}
