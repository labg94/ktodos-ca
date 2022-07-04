package com.example.ktodosca.repository.todo.mongo.dao

import com.example.ktodosca.repository.todo.mongo.dto.Todo
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Flux

interface TodoMongoDao : ReactiveCrudRepository<Todo, String> {

    fun findAllByStatus(status: String): Flux<Todo>

}
