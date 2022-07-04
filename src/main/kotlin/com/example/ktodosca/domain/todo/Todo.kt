package com.example.ktodosca.domain.todo

import java.time.LocalDateTime

data class Todo(
    val id: TodoId = TodoId(),
    val task: Task,
    val creationDate: LocalDateTime = LocalDateTime.now(),
    val lastUpdate: LocalDateTime = LocalDateTime.now(),
    val status: Status = Status.CREATED
               ) {

    fun updateStatus(): Todo {
        if (status === Status.COMPLETE) return this
        val newStatus = if (status === Status.WORKING_ON) Status.COMPLETE else Status.WORKING_ON
        return this.copy(status = newStatus, lastUpdate = LocalDateTime.now())
    }

    companion object {
        fun fromTask(task: String): Todo = Todo(task = Task(task))
    }

}
