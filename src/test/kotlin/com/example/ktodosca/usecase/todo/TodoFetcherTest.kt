package com.example.ktodosca.usecase.todo

import com.example.ktodosca.domain.todo.Status
import com.example.ktodosca.domain.todo.repository.GetTodos
import com.example.ktodosca.usecase.todo.mother.FakeGeTodoRepository
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource

class TodoFetcherTest {

    private val repository: GetTodos = FakeGeTodoRepository()
    private val useCase: TodoFetcher = TodoFetcherService(repository)


    @Test
    fun `when get all is called should get the same size as the repo`() {
        runBlocking {
            val useCaseCall = useCase.all().toList(mutableListOf())
            val repositoryCall = repository.all().toList(mutableListOf())

            assertEquals(repositoryCall, useCaseCall)
        }


    }

    @ParameterizedTest
    @EnumSource(Status::class)
    fun `when get all is called should get the same size as the repo`(status: Status) {
        runBlocking {
            val useCaseCall = useCase.byStatus(status).toList(mutableListOf())
            val repositoryCall = repository.withStatus(status).toList(mutableListOf())

            assertEquals(repositoryCall, useCaseCall)
        }


    }
}
