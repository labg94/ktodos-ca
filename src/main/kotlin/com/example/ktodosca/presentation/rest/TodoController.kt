package com.example.ktodosca.presentation.rest

import com.example.ktodosca.domain.todo.Status
import com.example.ktodosca.domain.todo.Todo
import com.example.ktodosca.domain.todo.TodoId
import com.example.ktodosca.presentation.rest.request.TaskRequest
import com.example.ktodosca.usecase.todo.NewTaskAdded
import com.example.ktodosca.usecase.todo.StatusChange
import com.example.ktodosca.usecase.todo.TodoFetcher
import kotlinx.coroutines.flow.Flow
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("todos")
class TodoController(
    private val newTask: NewTaskAdded,
    private val statusChange: StatusChange,
    private val fetcher: TodoFetcher
                    ) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    suspend fun addTodo(@RequestBody request: TaskRequest): Todo = newTask.createTodo(request.task)

    @GetMapping
    suspend fun getAll(): Flow<Todo> = fetcher.all()

    @GetMapping("{status}")
    suspend fun getByStatus(@PathVariable status: Status): Flow<Todo> = fetcher.byStatus(status)

    @PatchMapping("{id}")
    suspend fun updateNextStatus(@PathVariable id: String): Todo = statusChange.nextStatus(TodoId(id))

}
