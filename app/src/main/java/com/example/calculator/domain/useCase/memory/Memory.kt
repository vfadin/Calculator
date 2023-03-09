package com.example.calculator.domain.useCase.memory

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class Memory {
    private val _previousExpression = MutableStateFlow("")
    val previousExpression = _previousExpression.asStateFlow()

    fun saveExpression(data: String): String {
        _previousExpression.value = data
        return _previousExpression.value
    }
}