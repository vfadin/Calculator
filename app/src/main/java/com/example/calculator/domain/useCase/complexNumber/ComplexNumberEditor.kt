package com.example.calculator.domain.useCase.complexNumber

import com.example.calculator.domain.useCase.Editor
import com.example.calculator.domain.useCase.INumber
import com.example.calculator.utils.Constants
import kotlin.math.roundToInt

class ComplexNumberEditor : Editor() {
    override val keyboardValues = listOf(
        "7", "8", "9", "/", "4", "5", "6", "*", "1", "2", "3", "-", "0", "+", ".", "i*", "√"
    )
    override val delimiter = '.'
    private val operators = "+-*/"

    override fun setValue(number: INumber) {
        _expression.value = number.toString()
    }

    override fun addDelim(): String {
        _expression.value += '.'
        return "."
    }

    override fun addOperator(operator: Char): String {
        _expression.value += operator
        return "$operator"
    }
}