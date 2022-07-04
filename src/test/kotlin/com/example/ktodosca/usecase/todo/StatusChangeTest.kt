package com.example.ktodosca.usecase.todo

import com.example.ktodosca.domain.shared.error.OperationFailedException
import com.example.ktodosca.domain.shared.error.RegisterNotFoundException
import com.example.ktodosca.domain.todo.Status
import com.example.ktodosca.domain.todo.Todo
import com.example.ktodosca.domain.todo.repository.UpdateTodo
import com.example.ktodosca.usecase.todo.mother.FakeUpdateTodoRepository
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class StatusChangeTest {

    private val dummyTodo: Todo = Todo.fromTask("Dummy")
    private val notUpdateTodo = Todo.fromTask("I should not be updated").copy(status = Status.COMPLETE)
    private val repository: UpdateTodo = FakeUpdateTodoRepository(mutableListOf(dummyTodo, notUpdateTodo))
    private val useCase: StatusChange = StatusChangeService(repository)

    @Test
    fun `when the todo does not exist should throw an exception`() {

        val todo = Todo.fromTask("Dummy 2")
        val exceptionThrown = assertThrows(RegisterNotFoundException::class.java) {
            runBlocking { useCase.nextStatus(todo) }
        }
        assertEquals("the todo with Id ${todo.id} does not exists", exceptionThrown.message)
    }

    @Test
    fun `when exists but it could not be rewritten should throw an exception`() {
        val exceptionThrown = assertThrows(OperationFailedException::class.java) {
            runBlocking { useCase.nextStatus(notUpdateTodo) }
        }
        assertEquals("we could not update the todo with id ${notUpdateTodo.id}", exceptionThrown.message)
    }

    @Test

    fun `when is updated should return the todo with the new status`() {
        runBlocking {
            val updated = useCase.nextStatus(dummyTodo)
            assertEquals(updated.status.ordinal, dummyTodo.status.ordinal + 1)
        }
    }
}
