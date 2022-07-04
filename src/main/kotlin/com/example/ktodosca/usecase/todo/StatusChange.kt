package com.example.ktodosca.usecase.todo

import com.example.ktodosca.domain.todo.Todo

interface StatusChange {
  suspend  fun nextStatus(todo: Todo): Todo
}
