package com.example.ktodosca.repository.todo.mongo.dto

import com.example.ktodosca.domain.todo.Status
import com.example.ktodosca.domain.todo.Task
import com.example.ktodosca.domain.todo.TodoId
import com.example.ktodosca.domain.todo.Todo as Domain
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document
data class Todo(
    @Id val id: String,
    val task: String,
    val creationDate: LocalDateTime,
    val lastUpdate: LocalDateTime,
    val status: String
               ) {


    fun toDomain(): Domain = Domain(
        id = TodoId(id),
        task = Task(task),
        creationDate = creationDate,
        lastUpdate = lastUpdate,
        status = Status.valueOf(status)
                                   )
}
