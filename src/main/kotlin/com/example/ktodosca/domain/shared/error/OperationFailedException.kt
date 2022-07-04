package com.example.ktodosca.domain.shared.error

class OperationFailedException(override val message: String?) : Exception(message)
