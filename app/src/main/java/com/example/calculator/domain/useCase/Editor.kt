package com.example.calculator.domain.useCase

import com.example.calculator.domain.useCase.pNumber.Convert10p.Companion.intToChar
import com.example.calculator.domain.useCase.pNumber.ConvertP10.Companion.charToInt
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

abstract class Editor {
    private val delimiter = "."
    private val zero = "0"
    private val operators = "+-/*"
    private val _expression = MutableStateFlow("0")
    val expression = _expression.asStateFlow()

    fun addOperator(operator: Char): String {
        if (operators.contains(operator)) {
            if (operators.contains(_expression.value.last())) bs()
            _expression.value += operator
        }
        return _expression.value
    }

    fun addDigit(n: Int): String {
        if (n !in 0..15) {
            throw IndexOutOfBoundsException()
        }
        if (_expression.value == zero) {
            _expression.value = intToChar(n).toString()
        } else {
            _expression.value += intToChar(n)
        }
        return _expression.value
    }

    fun acc() =
        if (_expression.value.contains(delimiter)) _expression.value.length - _expression.value.indexOf(
            delimiter
        ) - 1 else 0

    fun addZero() = addDigit(0)

    fun addDelim(): String {
        if (_expression.value.isNotEmpty() && !_expression.value.contains(delimiter)) {
            _expression.value += delimiter
        }
        return _expression.value
    }

    fun bs(): String {
        _expression.value = if (_expression.value.length > 1) {
            _expression.value.removeRange(_expression.value.length - 1, _expression.value.length)
        } else {
            zero
        }
        return _expression.value
    }

    fun clear(): String {
        _expression.value = zero
        return _expression.value
    }

    fun doEdit(c: Char) = if (c == '.') {
        addDelim()
    } else if (operators.contains(c)) {
        addOperator(c)
    } else {
        addDigit(charToInt(c))
    }
}