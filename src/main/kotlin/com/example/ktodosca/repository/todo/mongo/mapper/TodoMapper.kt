package com.example.ktodosca.repository.todo.mongo.mapper

import com.example.ktodosca.domain.todo.Todo
import com.example.ktodosca.repository.todo.mongo.dto.Todo as Dto

fun Todo.toDto(): Dto = Dto(
    id = id.value, task = task.value, creationDate = creationDate, lastUpdate = lastUpdate, status = status.name
                                )
