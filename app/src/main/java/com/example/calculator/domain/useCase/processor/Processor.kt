package com.example.calculator.domain.useCase.processor

import com.example.calculator.domain.useCase.INumber
import com.example.calculator.domain.useCase.pNumber.PNumber
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNotNull

class Processor {
    private val _lastOperation = MutableStateFlow<String?>(null)
    val lastOperation = _lastOperation.asStateFlow().filterNotNull()
    lateinit var leftOperand: INumber
    lateinit var rightOperand: INumber
    lateinit var answer: INumber
    val operator = '+'

    fun calculate(leftOperand: INumber, rightOperand: INumber, operator: Char): INumber {
        _lastOperation.value = "$leftOperand $operator $rightOperand"
        answer = when(operator) {
            '+' -> leftOperand + rightOperand
            '-' -> leftOperand - rightOperand
            '*' -> leftOperand * rightOperand
            '/' -> leftOperand / rightOperand
            else -> leftOperand
        }
        return answer
    }
}