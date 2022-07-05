package com.example.ktodosca.presentation.todo.graphql

import com.example.ktodosca.domain.todo.Status
import com.example.ktodosca.usecase.todo.TodoFetcher
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.CrossOrigin

@Controller
@CrossOrigin("*")
class TodoGraphql(
    private val fetcher: TodoFetcher
                 ) {


    @QueryMapping
    suspend fun getAll() = fetcher.all()


    @QueryMapping
    suspend fun getByStatus(@Argument status: Status) = fetcher.byStatus(status)

}
