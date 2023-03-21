package com.example.calculator.domain.useCase.processor

import com.example.calculator.domain.useCase.INumber
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNotNull

class Processor {
    private val _lastOperation = MutableStateFlow<String?>(null)
    val lastOperation = _lastOperation.asStateFlow().filterNotNull()
    private lateinit var answer: INumber

    fun calculate(leftOperand: INumber, rightOperand: INumber, operator: Char): INumber {
        _lastOperation.value = "$leftOperand $operator $rightOperand"
        answer = when(operator) {
            '+' -> leftOperand + rightOperand
            '-' -> leftOperand - rightOperand
            '*' -> leftOperand * rightOperand
            '/' -> leftOperand / rightOperand
            '√' -> leftOperand.squared()
            else -> leftOperand
        }
        return answer
    }

     fun clear() {
         _lastOperation.value = ""
     }
}