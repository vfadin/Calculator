package com.example.calculator.domain.useCase.pNumber

import com.example.calculator.domain.useCase.Editor

class PNumberEditor : Editor() {
    override val SPAN_COUNT = 5
    override val keyboardValues = listOf(
        "C", "D", "E", "F", "/", "8", "9", "A", "B", "*", "4", "5", "6", "7", "-", "0",
        "1", "2", "3", "+", "."
    )
    var base = 16
        private set

    override fun addDigit(n: Int): String {
        if (n !in 0 until base) {
            return _expression.value
        }
        if (_expression.value == zero) {
            _expression.value = Convert10p.intToChar(n).toString()
        } else {
            _expression.value += Convert10p.intToChar(n)
        }
        return _expression.value
    }

    fun setValue(number: PNumber, editor: PNumberEditor) {
        _expression.value = number.toString()
    }

    fun setBase(base: Int) {
        this.base = base
    }
}