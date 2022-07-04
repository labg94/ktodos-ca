package com.example.ktodosca.presentation.rest

import com.example.ktodosca.domain.todo.Status
import com.example.ktodosca.domain.todo.Todo
import com.example.ktodosca.presentation.rest.request.TaskRequest
import com.example.ktodosca.repository.todo.memory.InMemoryRepository
import com.example.ktodosca.shared.mother.todos
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.web.reactive.function.BodyInserters


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("memory")
class TodoControllerTest {


    @Autowired
    private lateinit var webClient: WebTestClient


    @Autowired
    private lateinit var repository: InMemoryRepository

    private val todos = todos()

    @BeforeEach
    internal fun setUp() {
        runBlocking {
            todos.map { repository.addTodo(it) }
        }
    }

    @AfterEach
    internal fun tearDown() {
        repository.deleteAll()
    }

    @Test
    fun `when the task is added should create the todo`() {
        val (id) = webClient.post()
                .uri("/todos")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(TaskRequest("My Task")))
                .exchange()
                .expectStatus()
                .isCreated
                .returnResult(Todo::class.java)
                .responseBody.blockFirst()!!

        runBlocking {
            assertTrue(repository.existsById(id))
        }
    }


    @Test
    fun `when the getAll is called should bring all the todos`() {

        webClient.get()
                .uri("/todos")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk
                .expectBody().jsonPath("$.size()").isEqualTo(todos.size)
    }

    @ParameterizedTest
    @EnumSource(Status::class)
    fun `when the getByStatus is called should bring all the todos with that status`(status: Status) {

        val todosWithStatus = todos.filter { it.status === status }

        webClient.get()
                .uri("/todos/$status")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk
                .expectBody().jsonPath("$.size()").isEqualTo(todosWithStatus.size)
    }

    @Test
    fun `when the update is called the todo should has the new status`() {
        val todo = todos.first()

        val todoUpdated = callTheUpdate(todo)

        assertEquals(newStatus(todo), todoUpdated.status)
    }

    @Test
    fun `when the update is called the todo should be updated in the db`() {
        val todo = todos.first()

        callTheUpdate(todo)

        runBlocking {
            val statusFound = repository.all().filter { it.id == todo.id }.map { it.status }.first()
            assertEquals(newStatus(todo), statusFound)
        }
    }

    private fun newStatus(todo: Todo) =
        if (todo.status === Status.CREATED) Status.WORKING_ON
        else Status.COMPLETE

    private fun callTheUpdate(todo: Todo) = webClient.patch()
            .uri("/todos/${todo.id.value}")
            .contentType(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk
            .returnResult(Todo::class.java)
            .responseBody.blockFirst()!!
}
