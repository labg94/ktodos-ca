package com.example.ktodosca.usecase.todo

import com.example.ktodosca.domain.shared.error.OperationFailedException
import com.example.ktodosca.domain.shared.error.RegisterNotFoundException
import com.example.ktodosca.domain.todo.Todo
import com.example.ktodosca.domain.todo.repository.UpdateTodo

class StatusChangeService(private val repository: UpdateTodo) : StatusChange {
    override suspend fun nextStatus(todo: Todo): Todo =
        if (repository.existsById(todo.id)) removeAndUpdate(todo)
        else throw RegisterNotFoundException("the todo with Id ${todo.id} does not exists")

    private suspend fun removeAndUpdate(todo: Todo): Todo =
        if (repository.remove(todo.id)) repository.update(todo.updateStatus())
        else throw OperationFailedException("we could not update the todo with id ${todo.id}")
}
