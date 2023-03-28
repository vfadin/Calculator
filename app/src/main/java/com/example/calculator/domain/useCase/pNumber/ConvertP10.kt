package com.example.calculator.domain.useCase.pNumber

import kotlin.math.log2
import kotlin.math.pow

class ConvertP10 {
    companion object {
        fun charToInt(c: Char): Int {
//            if (c == 'i') {
//            }
            val index = "0123456789ABCDEF".indexOf(c)
            if (index == -1) {
                throw IndexOutOfBoundsException()
            }
            return index
        }

        fun convert(pValue: String, p: Int, weight: Double): Double {
            if (weight % p != 0.0) {
                throw Exception()
            }
            var degree = (log2(weight) / log2(p.toDouble()) - 1).toInt()
            var result = 0.0
            for (i in pValue.indices) {
                result += charToInt(pValue[i]) * p.toDouble().pow(degree--)
            }
            return result
        }

        fun dval(pValue: String, p: Int): Double {
            val parts = pValue.split(".")
            var integerPart = 0.0
            for (i in parts[0].indices) {
                if (parts[0][i] == '-') continue
                integerPart += charToInt(parts[0][i]) * p.toDouble()
                    .pow((parts[0].length - 1 - i).toDouble())
            }
            var fractionalPart = 0.0
            if (parts.size == 2) {
                for (i in parts[1].indices) {
                    fractionalPart += charToInt(parts[1][i]) * p.toDouble()
                        .pow(-(i + 1).toDouble())
                }
            }
            return (integerPart + fractionalPart) * if (pValue.first() == '-') -1 else 1
        }
    }
}