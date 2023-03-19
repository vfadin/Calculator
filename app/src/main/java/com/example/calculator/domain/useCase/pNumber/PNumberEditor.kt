package com.example.calculator.domain.useCase.pNumber

import com.example.calculator.domain.useCase.Editor

class PNumberEditor : Editor() {

    var base = 2

    fun setValue(answer: String) {
        _expression.value = answer
    }

    val keyboardValues = listOf(
        "7", "8", "9", "/", "4", "5", "6", "*", "1", "2", "3", "-", "0", ".", "+", "A", "B","C", "D", "E", "F"
    )
}