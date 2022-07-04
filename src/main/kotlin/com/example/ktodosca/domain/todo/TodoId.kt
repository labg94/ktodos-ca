package com.example.ktodosca.domain.todo

import java.util.*

@JvmInline
value class TodoId(val value: String = "TODO-${ UUID.randomUUID() }")
