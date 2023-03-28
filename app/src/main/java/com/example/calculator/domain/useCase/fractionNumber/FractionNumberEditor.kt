package com.example.calculator.domain.useCase.fractionNumber

import com.example.calculator.domain.useCase.Editor
import com.example.calculator.utils.Constants.Companion.OPERATORS_FRACTION

class FractionNumberEditor : Editor() {
    override val delimiter = '/'
    private val operators = "+-*:"
    override val keyboardValues = listOf(
        "7", "8", "9", "/", "4", "5", "6", "*", "1", "2", "3", "-", "0", "+", "√", ":"
    )

    override fun addDelim(): String {
        return _expression.value.apply {
            if (!contains(delimiter))
                _expression.value += delimiter
            else if (count { it == delimiter } < 2 && contains(OPERATORS_FRACTION))
                _expression.value += delimiter
        }
    }

    override fun addOperator(operator: Char): String {
        if (isSqr) return _expression.value
        if (operators.contains(operator)) {
            if (operators.contains(_expression.value.last())) bs()
            _expression.value.apply {
                if (!contains(OPERATORS_FRACTION)) {
                    _expression.value += operator
                } else if (contains('-') && contains(delimiter)) {
                    _expression.value += operator
                }
            }
        }
        return _expression.value
    }
}