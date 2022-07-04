package com.example.ktodosca.repository.todo.mongo

import com.example.ktodosca.domain.todo.Status
import com.example.ktodosca.domain.todo.Todo
import com.example.ktodosca.domain.todo.TodoId
import com.example.ktodosca.repository.todo.mongo.dao.TodoMongoDao
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean

@DataMongoTest
class TodoMongoRepositoryTest {

    @Autowired
    lateinit var dao: TodoMongoDao

    @Autowired
    lateinit var repository: TodoMongoRepository

    @AfterEach
    internal fun tearDown() {
        dao.deleteAll().block()
    }

    @Test
    fun `save a new Todo`() {
        runBlocking {
            val todoAdded = repository.addTodo(Todo.fromTask("Dummy"))

            assertTrue(repository.existsById(todoAdded.id), "the todo should exists after being added")
        }
    }


    @Nested
    inner class LoadedTodos {
        private val todos: List<Todo> = (1..10)
                .map { Todo.fromTask(it.toString()).copy(status = Status.values()[it % 3]) }

        @BeforeEach
        fun setUp() {
            runBlocking {
                todos.forEach { repository.addTodo(it) }
            }
        }


        @Test
        fun `when get all is called should return all the saved ones`() {

            runBlocking {
                val all = repository.all().toList(mutableListOf())
                assertEquals(todos.size, all.size)
            }

        }

        @Test
        fun `when existById is called and the todo was saved before should return true`() {
            runBlocking {
                assertTrue(repository.existsById(todos.first().id))
            }
        }

        @Test
        fun `when existById is called and the todo was not saved before should return false`() {
            runBlocking {
                assertFalse(repository.existsById(TodoId()))
            }
        }

        @Test
        fun `when withStatus is called should  return only th values with that status`() {
            runBlocking {

                val valuesFiltered = repository.withStatus(Status.CREATED).toList(mutableListOf())

                assertEquals(todos.filter { it.status === Status.CREATED }.size, valuesFiltered.size)
            }
        }


        @Test
        fun `when remove is called and the todo existed before should return true`() {
            runBlocking {
                assertTrue(repository.remove(todos.first().id))
            }
        }


        @Test
        fun `when update is called should rewrite the old value`() {
            runBlocking {
                val todo = todos.first()
                val updated = repository.update(todo.updateStatus())

                val withStatus = repository.withStatus(updated.status).toList(mutableListOf())

                assertNotNull(withStatus.first { it.id.value == updated.id.value })
            }
        }
    }

    @TestConfiguration
    class BeanConfig {

        @Bean
        fun repository(dao: TodoMongoDao): TodoMongoRepository = TodoMongoRepository(dao)

    }

}

