package com.example.ktodosca.repository.todo.memory

import com.example.ktodosca.domain.todo.Status
import com.example.ktodosca.domain.todo.Todo
import com.example.ktodosca.shared.mother.todos
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class InMemoryRepositoryTest {

    private val repository: InMemoryRepository = InMemoryRepository()


    @Test
    fun `when a todo is added should be stored`() {
        runBlocking {
            val todo = Todo.fromTask("dummy")
            val todoAdded = repository.addTodo(todo)
            val found = repository.all().filter { it === todoAdded }.first()
            assertEquals(todoAdded, found)
        }
    }

    @Test
    fun `Several values added and stored`() {

        runBlocking {
            (1..10)
                    .map { Todo.fromTask(it.toString()) }
                    .map { repository.addTodo(it) }


            val filtered =
                repository.all().toList(mutableListOf())

            assertEquals(10, filtered.size)
        }


    }


    @Test
    fun `when is filter by status should return all by that status`() {

        runBlocking {
            todos().map { repository.addTodo(it) }


            val filtered =
                repository.withStatus(Status.COMPLETE)
                        .map { it.status }
                        .toList(mutableListOf())

            assertTrue(filtered.all { it === Status.COMPLETE }, "All the values should have the same status")
        }


    }

    @Test
    internal fun `when existsById is called and exists should return true`() {
        runBlocking {
            val fromTask = Todo.fromTask("dummy")

            repository.addTodo(fromTask)

            assertTrue(repository.existsById(fromTask.id))
        }

    }

    @Test
    internal fun `when existsById is called and there is not a todo with the id should return false`() {
        runBlocking {
            val fromTask = Todo.fromTask("dummy")

            assertFalse(repository.existsById(fromTask.id))
        }

    }

    @Test
    internal fun `when findById is called and exists should return the same stored`() {
        runBlocking {
            val fromTask = Todo.fromTask("dummy")

            repository.addTodo(fromTask)

            assertEquals(fromTask, repository.findById(fromTask.id))
        }

    }

    @Test
    internal fun `when findById is called and there is not a todo with the id should return false`() {
        runBlocking {
            val fromTask = Todo.fromTask("dummy")

            assertNull(repository.findById(fromTask.id))
        }

    }

    @Test
    internal fun `when remove is called and there is not a todo with the id should return false`() {
        runBlocking {
            val fromTask = Todo.fromTask("dummy")

            assertFalse(repository.remove(fromTask.id))
        }

    }


    @Test
    internal fun `when remove is called and the todo exists should return true`() {
        runBlocking {
            val fromTask = Todo.fromTask("dummy")

            repository.addTodo(fromTask)


            assertTrue(repository.remove(fromTask.id))
        }

    }

    @Test
    internal fun `when remove is called and the todo exists should be deleted`() {
        runBlocking {
            val fromTask = Todo.fromTask("dummy")

            repository.addTodo(fromTask)
            repository.remove(fromTask.id)
            assertFalse(repository.existsById(fromTask.id))
        }
    }

    @Test
    internal fun `when update is called should add the new value`() {
        runBlocking {
            val todo = Todo.fromTask("dummy")
            val todoUpdated = repository.update(todo)
            val found = repository.all().filter { it === todoUpdated }.first()
            assertEquals(todoUpdated, found)
        }
    }
}
