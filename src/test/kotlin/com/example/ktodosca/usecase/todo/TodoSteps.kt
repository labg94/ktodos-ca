package com.example.ktodosca.usecase.todo

import com.example.ktodosca.domain.todo.Status
import com.example.ktodosca.domain.todo.Todo
import com.example.ktodosca.repository.todo.memory.InMemoryRepository
import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals

class TodoSteps {

    private val repository: InMemoryRepository = InMemoryRepository()
    private val newTaskAdded: NewTaskAdded = NewTaskAddedService(repository)
    private lateinit var task: String
    private lateinit var todo: Todo

    @Given("a new task {string}")
    fun `a new task`(task: String) {
        this.task = task
    }

    @When("I added it")
    fun `I Added It`() {
        runBlocking {
            todo = newTaskAdded.createTodo(task)
        }
    }

    @Then("I should receive a new Todo with {string} status")
    fun `I Should Receive A New Todo With Created Status`(status: String) {
        assertEquals(todo.status, Status.valueOf(status))
    }
}
