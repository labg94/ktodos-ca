package com.example.ktodosca.usecase.todo.mother

import com.example.ktodosca.domain.todo.Status
import com.example.ktodosca.domain.todo.Todo
import com.example.ktodosca.domain.todo.repository.GetTodos
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow

class FakeGeTodoRepository : GetTodos {

    private val todos: List<Todo> = (1..10)
            .map { value -> Todo.fromTask(value.toString()) }
            .mapIndexed { index, todo ->
                if (index < 6) todo
                else if (index < 8) todo.copy(status = Status.WORKING_ON)
                else todo.copy(status = Status.COMPLETE)
            }


    override suspend fun all(): Flow<Todo> = todos.asFlow()

    override suspend fun withStatus(status: Status): Flow<Todo> =
        todos.filter { it.status === status }.asFlow()
}
