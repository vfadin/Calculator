package com.example.calculator.domain.useCase.pNumber

import com.example.calculator.domain.useCase.Editor
import com.example.calculator.domain.useCase.pNumber.Convert10p.Companion.doP

class PNumberEditor : Editor() {
    override val SPAN_COUNT = 5
    override val keyboardValues = listOf(
        "F", "E", "D", "C", "/", "B", "A", "9", "8", "*", "7", "6", "5", "4", "-", "3", "2", "1",
        ".", "+"
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