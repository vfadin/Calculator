package com.example.calculator.domain.useCase.pNumber

import kotlin.math.abs

class Convert10p {
    companion object {
        /**
         * Convert integer to character
         * @param d Integer to be convert to char
         */
        fun intToChar(d: Int): Char {
            if (d !in 0..15) {
                throw IndexOutOfBoundsException()
            }
            return "0123456789ABCDEF"[d]
        }

        /**
         * Convert decimal integer to base p number
         */
        fun intToP(n: Long, p: Long): String {
            if (p !in 2..16) {
                throw IllegalArgumentException("Base must be between 2 and 16 inclusive.")
            }
            if (n == 0L) {
                return "0"
            }
            var x = abs(n)
            var result = ""
            while (x > 0) {
                val digit = x % p
                result += (intToChar(digit.toInt()))
                x /= p
            }
            result += if (n < 0) "-" else ""
            return result.reversed()
        }

        /**
         * Convert decimal fraction to base p number
         */
        fun doP(n: Double, p: Int, c: Int): String {
            if (p !in 2..16) {
                throw IllegalArgumentException("Base must be between 2 and 16 inclusive.")
            }
            return StringBuilder().apply {
                append(intToP(n.toLong(), p.toLong()))
                if (c != 0) append(".")
                append(fltToP(n, p, c))
            }.toString()
        }

        /**
         * Convert decimal real number to base p number
         */
        fun fltToP(n: Double, p: Int, c: Int): String {
            if (p !in 2..16) {
                throw IllegalArgumentException("Base must be between 2 and 16 inclusive.")
            }
            var x = n
            return StringBuilder().apply {
                for (i in 0 until c) {
                    x = (x - x.toLong()) * p
                    append(intToChar(x.toInt()))
                }
            }.toString()
        }
    }
}
