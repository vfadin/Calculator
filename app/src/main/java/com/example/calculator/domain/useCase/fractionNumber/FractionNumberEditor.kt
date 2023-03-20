package com.example.calculator.domain.useCase.fractionNumber

import com.example.calculator.domain.useCase.Editor
import com.example.calculator.utils.Constants
import com.example.calculator.utils.Constants.Companion.OPERATORS
import com.example.calculator.utils.Constants.Companion.OPERATORS_FRACTION

class FractionNumberEditor : Editor() {
    override val delimiter = '/'
    private val operators = "+-*"

    fun setValue(answer: String) {
        _expression.value = answer
    }

    override fun addDelim(): String {
        return _expression.value.apply {
            if (!contains(delimiter))
                _expression.value += delimiter
            if (count { it == delimiter } < 2 && contains(OPERATORS_FRACTION))
                _expression.value += delimiter
        }
    }

    override fun addOperator(operator: Char): String {
        if (operators.contains(operator)) {
            if (operators.contains(_expression.value.last())) bs()
            _expression.value.apply {
                if (!contains(OPERATORS_FRACTION) && contains(delimiter)) {
                    _expression.value += operator
                }
            }
        }
        return _expression.value
    }
}