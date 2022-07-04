package com.example.ktodosca.usecase.todo

import com.example.ktodosca.domain.shared.error.OperationFailedException
import com.example.ktodosca.domain.shared.error.RegisterNotFoundException
import com.example.ktodosca.domain.todo.Todo
import com.example.ktodosca.domain.todo.TodoId
import com.example.ktodosca.domain.todo.repository.UpdateTodo
import org.springframework.stereotype.Service

@Service
class StatusChangeService(private val repository: UpdateTodo) : StatusChange {
    override suspend fun nextStatus(id: TodoId): Todo {
        val todo = (repository.findById(id).apply { println("THE VALUE $this")  } ?: throw RegisterNotFoundException("the todo with Id $id does not exists"))
        return removeAndUpdate(
            todo
                              )
    }


    private suspend fun removeAndUpdate(todo: Todo): Todo =
        if (repository.remove(todo.id)) repository.update(todo.updateStatus())
        else throw OperationFailedException("we could not update the todo with id ${todo.id}")
}
