package com.example.ktodosca.usecase.todo.mother

import com.example.ktodosca.domain.todo.Todo
import com.example.ktodosca.domain.todo.repository.CreateTodo

class FakeCreateTodoRepository(private val todos: MutableList<Todo>) : CreateTodo {
    override suspend fun addTodo(todo: Todo): Todo {
        todos.add(todo)
        return todo
    }
}
