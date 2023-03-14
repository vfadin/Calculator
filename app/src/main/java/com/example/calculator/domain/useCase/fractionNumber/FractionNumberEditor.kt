package com.example.calculator.domain.useCase.fractionNumber

import com.example.calculator.domain.useCase.Editor
import com.example.calculator.utils.Constants

class FractionNumberEditor: Editor() {
    private val delimiter = '/'
    private val operators = "+-*"

    fun setValue(answer: String) {
        _expression.value = answer
    }

    val keyboardValues = listOf(
        "7", "8", "9", "/", "4", "5", "6", "*", "1", "2", "3", "-", "0", "+"
    )

    override fun addDelim(): String {
        if (_expression.value.isNotEmpty() && !_expression.value.contains(delimiter)) {
            _expression.value += delimiter
        }
        return _expression.value
    }

    override fun addOperator(operator: Char): String {
        if (operators.contains(operator)) {
            if (operators.contains(_expression.value.last())) bs()
            if (!_expression.value.contains(Constants.OPERATORS)) _expression.value += operator
        }
        return _expression.value
    }
}