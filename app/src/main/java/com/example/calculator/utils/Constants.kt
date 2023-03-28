package com.example.calculator.utils

class Constants {
    companion object {
        val OPERATORS = Regex("[+\\-*/:]")
        val OPERATORS_FRACTION = Regex("[+\\-*:]")
        val COMPLEX_NUMBER_PATTERN = Regex("""([-+]?\d*\.?\d+)([-+])?i\*(\d*\.?\d*)""")
    }
}