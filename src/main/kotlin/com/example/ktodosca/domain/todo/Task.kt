package com.example.ktodosca.domain.todo

import com.example.ktodosca.domain.shared.error.InvalidInputException

@JvmInline
value class Task(val value: String) {
    init {
        if (value.isBlank()) throw InvalidInputException("the task should not be blank")
    }

}
