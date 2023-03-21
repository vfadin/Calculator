package com.example.calculator.domain.useCase

import com.example.calculator.domain.useCase.pNumber.Convert10p.Companion.intToChar
import com.example.calculator.domain.useCase.pNumber.ConvertP10.Companion.charToInt
import com.example.calculator.utils.Constants.Companion.OPERATORS
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

abstract class Editor {
    open val delimiter = '.'
    protected val zero = "0"
    private val operators = "+-/*"
    protected val _expression = MutableStateFlow("0")
    protected val isSqr = _expression.value.contains("√")
    val expression = _expression.asStateFlow()
    open val keyboardValues = listOf(
        "7", "8", "9", "/", "4", "5", "6", "*", "1", "2", "3", "-", "0", ".", "+"
    )
    open val SPAN_COUNT = 4

    open fun setValue(number: INumber) {
        _expression.value = number.toString()
    }

    fun setValue(text: String) {
        _expression.value = text
    }

    open fun addOperator(operator: Char): String {
        if (isSqr) return _expression.value
        if (operators.contains(operator)) {
            if (operators.contains(_expression.value.last())) bs()
            if (!_expression.value.contains(OPERATORS)) _expression.value += operator
        }
        return _expression.value
    }

    open fun addDigit(n: Int): String {
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
        if (_expression.value.contains(delimiter))
            _expression.value.length - _expression.value.indexOf(delimiter) - 1
        else 0

    fun addZero() = addDigit(0)

    open fun addDelim(): String {
        return _expression.value.apply {
            if (!contains(delimiter) || (count { it == delimiter } < 2 && contains(OPERATORS)))
                _expression.value += delimiter
        }
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

    fun doEdit(c: Char) = if (c == delimiter) {
        addDelim()
    } else if (operators.contains(c)) {
        addOperator(c)
    } else if (c == '√') {
        addSqrt(c)
    } else {
        addDigit(charToInt(c))
    }

    private fun addSqrt(c: Char): String {
        _expression.value = c.toString()
        return _expression.value
    }
}