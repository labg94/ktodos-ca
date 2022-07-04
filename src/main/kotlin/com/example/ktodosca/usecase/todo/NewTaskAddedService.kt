package com.example.ktodosca.usecase.todo

import com.example.ktodosca.domain.todo.Todo
import com.example.ktodosca.domain.todo.repository.CreateTodo
import org.springframework.stereotype.Service


@Service
class NewTaskAddedService(private val repository: CreateTodo) : NewTaskAdded {
    override suspend fun createTodo(task: String): Todo = repository.addTodo(Todo.fromTask(task))
}
