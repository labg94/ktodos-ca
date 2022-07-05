package com.example.ktodosca.presentation.todo.graphql

import com.example.ktodosca.domain.todo.Status
import com.example.ktodosca.domain.todo.Todo
import com.example.ktodosca.domain.todo.TodoId
import com.example.ktodosca.usecase.todo.NewTaskAdded
import com.example.ktodosca.usecase.todo.StatusChange
import com.example.ktodosca.usecase.todo.TodoFetcher
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.CrossOrigin

@Controller
@CrossOrigin("*")
class TodoGraphql(
    private val newTask: NewTaskAdded,
    private val statusChange: StatusChange,
    private val fetcher: TodoFetcher
                 ) {

    @QueryMapping
    suspend fun getAll() = fetcher.all()


    @QueryMapping
    suspend fun getByStatus(@Argument status: Status) = fetcher.byStatus(status)

    @MutationMapping
    suspend fun createTodo(@Argument task: String): Todo = newTask.createTodo(task)

    @MutationMapping
    suspend fun nextStatus(@Argument id: String) = statusChange.nextStatus(TodoId(id))
}
