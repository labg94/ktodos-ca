package com.example.ktodosca.domain.todo

import com.example.ktodosca.domain.shared.error.InvalidInputException
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class TodoTest {
    private val task: String = "implement some methods"
    private val todo: Todo = Todo.fromTask(task = task)

    @Nested
    @DisplayName("when a valid task is supplied ")
    inner class ValidTaskCreation {


        @Test
        fun ` then a todo should be created with the value of the task`() = assertEquals(todo.task.value, task)

        @Test
        fun `then the status should be 'CREATED'`() = assertEquals(todo.status, Status.CREATED)

        @Test
        fun `then should has an id starting with TODO`() = assertTrue(todo.id.value.startsWith("TODO-"))

        @Test
        fun `then the creation date should be in the moment`() {
            val creationDate = todo.creationDate
            val oneSecondBefore = LocalDateTime.now().minusSeconds(1)
            val oneSecondLater = LocalDateTime.now().plusSeconds(1)

            assertTrue(creationDate.isAfter(oneSecondBefore) && creationDate.isBefore(oneSecondLater))
        }

        @Test
        fun `then the lastUpdate date should be in the moment`() {
            val lastUpdate = todo.lastUpdate
            val oneSecondBefore = LocalDateTime.now().minusSeconds(1)
            val oneSecondLater = LocalDateTime.now().plusSeconds(1)

            assertTrue(lastUpdate.isAfter(oneSecondBefore) && lastUpdate.isBefore(oneSecondLater))
        }
    }

    @Nested
    inner class InvalidTaskCreation {

        @Test
        fun `when the task is empty should throw an error`() {
            val exception = assertThrows(InvalidInputException::class.java) { Todo.fromTask(task = "") }
            assertEquals(exception.message, "the task should not be blank")
        }

        @Test
        fun `when the task is blank should throw an error`() {
            val exception = assertThrows(InvalidInputException::class.java) { Todo.fromTask(task = "   ") }
            assertEquals(exception.message, "the task should not be blank")
        }
    }


    @Nested
    @DisplayName("when the update method is called")
    inner class UpdateStatus {

        @Test
        fun `and the initial status is CREATED should be updated to WORKING ON`() {
            val todoUpdated = todo.updateStatus()
            assertEquals(Status.WORKING_ON, todoUpdated.status)
        }

        @Test
        fun `and the status is updated then the lastUpdate should be now`() {
            val yesterdaysTodo = todo.copy(lastUpdate = LocalDateTime.now().minusDays(1))
            val todoUpdated = yesterdaysTodo.updateStatus()
            assertTrue(todoUpdated.lastUpdate.isAfter(yesterdaysTodo.lastUpdate))
        }

        @Test
        fun `and the status is not updated then the lastUpdate should be the same as before`() {
            val yesterdaysTodo = todo.copy(lastUpdate = LocalDateTime.now().minusDays(1), status = Status.COMPLETE)
            val todoUpdated = yesterdaysTodo.updateStatus()
            assertEquals(todoUpdated.lastUpdate, yesterdaysTodo.lastUpdate)
        }

        @Test
        fun `and the initial status is WORKING ON should be updated to COMPLETE`() {
            val todoUpdated = todo.copy(status = Status.WORKING_ON).updateStatus()
            assertEquals(Status.COMPLETE, todoUpdated.status)
        }

        @Test
        fun `and the initial status is COMPLETE should return the same todo`() {
            val complete = todo.copy(status = Status.COMPLETE)
            val todoUpdated = complete.updateStatus()
            assertSame(complete, todoUpdated)
        }
    }

}
