package com.example.calculator.domain.useCase.pNumber

import com.example.calculator.domain.useCase.Editor

class PNumberEditor : Editor() {
    val keyboardValues = listOf(
        "7", "8", "9", "/", "4", "5", "6", "*", "1", "2", "3", "-", "0", ".", "=", "+"
    )
}