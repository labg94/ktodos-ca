package com.example.ktodosca.shared.mother

import com.example.ktodosca.domain.todo.Status
import com.example.ktodosca.domain.todo.Todo

fun todos() = (1..10)
        .map { value -> Todo.fromTask(value.toString()) }
        .mapIndexed { index, todo ->
            if (index < 6) todo
            else if (index < 8) todo.copy(status = Status.WORKING_ON)
            else todo.copy(status = Status.COMPLETE)
        }
