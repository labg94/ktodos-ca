package com.example.ktodosca.usecase.todo.mother

import com.example.ktodosca.domain.todo.Status
import com.example.ktodosca.domain.todo.Todo
import com.example.ktodosca.domain.todo.TodoId
import com.example.ktodosca.domain.todo.repository.UpdateTodo

class FakeUpdateTodoRepository(private val todos: MutableList<Todo>) : UpdateTodo {
    override suspend fun existsById(id: TodoId): Boolean = todos.find { it.id.value === id.value } !== null

    override suspend fun update(todo: Todo): Todo = todo.apply { todos.add(this) }

    override suspend fun remove(id: TodoId): Boolean {
        if (todos.find { it.id.value === id.value }?.status === Status.COMPLETE) return false
        return todos.removeIf { it.id.value === id.value }
    }

}
