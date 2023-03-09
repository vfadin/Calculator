package com.example.calculator.utils

class Constants {
    companion object {
        enum class Operators(char: Char) {
            PLUS('+'),
            MINUS('-'),
            DIVIDE('\\'),
            MULTIPLY('*')
        }
    }
}