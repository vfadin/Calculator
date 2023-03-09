package com.example.calculator.domain.useCase.processor

import com.example.calculator.domain.useCase.INumber
import com.example.calculator.domain.useCase.pNumber.PNumber
import com.example.calculator.utils.Constants

class Processor {
    lateinit var leftOperand: INumber
    lateinit var rightOperand: INumber
    val operator = '+'

    fun calculate(expression: String) {
        leftOperand = PNumber(0.3)
        rightOperand = PNumber(0.4)
        println(leftOperand + rightOperand)
        val slices = expression.split('+')
        // TODO: здесь просто разветвление на вычисления класса 'Число'
    }
}