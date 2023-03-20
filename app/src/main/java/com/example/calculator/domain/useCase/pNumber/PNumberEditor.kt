package com.example.calculator.domain.useCase.pNumber

import com.example.calculator.domain.useCase.Editor

class PNumberEditor : Editor() {
    override val SPAN_COUNT = 5
    var base = 2

    fun setValue(number: PNumber, editor: PNumberEditor) {
        _expression.value = Convert10p.doP(number.number, editor.base, editor.acc())
    }

    override val keyboardValues = listOf(
        "F", "E", "D", "C", "/", "B", "A", "9", "8", "*", "7", "6", "5", "4", "-", "3", "2", "1",
        ".", "+"
    )
}