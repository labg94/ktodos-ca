package com.example.ktodosca.usecase.todo

import com.example.ktodosca.domain.todo.Todo
import com.example.ktodosca.domain.todo.repository.CreateTodo
import com.example.ktodosca.usecase.todo.mother.FakeCreateTodoRepository
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class NewTaskAddedTest {

    private val todos = mutableListOf<Todo>()
    private val repository: CreateTodo = FakeCreateTodoRepository(todos)
    private val useCase: NewTaskAdded = NewTaskAddedService(repository)


    @Test
    fun `when a task is given should create a task`() {
        runBlocking {
            val task = "my dummy task"
            val createTodo = useCase.createTodo(task)
            assertEquals(task, createTodo.task.value)
        }

    }

    @Test
    fun `when a task is given should create a task and store it`() {
        runBlocking {
            val task = "my dummy task"
            useCase.createTodo(task)
            assertEquals(1, todos.size)
        }

    }

    @Test
    fun `when a task is given should create a task and store its value`() {
        runBlocking {
            val task = "my dummy task"
            val createTodo = useCase.createTodo(task)
            val found = todos.find { it === createTodo }
            assertNotNull(found)
        }

    }
}

