package com.example.calculator.domain.useCase.fractionNumber

import com.example.calculator.domain.useCase.INumber

class FractionNumber(numerator: Long, denominator: Long = 1L) : INumber {
    var numerator = 0L
        private set
    var denominator = 1L
        private set

    init {
        this.numerator = numerator
        this.denominator = denominator
    }


    override fun plus(a: INumber): INumber {
        if ((a as FractionNumber).denominator == 0L) return this
        if (denominator == 0L) return a
        val cDenominator = lcm(a.denominator, denominator)
        val cNumerator = a.numerator * (cDenominator / a.denominator) +
                numerator * (cDenominator / denominator)
        return FractionNumber(cNumerator, cDenominator)
    }

    override fun minus(a: INumber): INumber {
        if ((a as FractionNumber).denominator == 0L) {
            numerator *= -1
            return this
        }
        if (denominator == 0L) return a
        val cDenominator: Long = lcm(a.denominator, denominator)
        val cNumerator: Long = a.numerator * (cDenominator / a.denominator) -
                numerator * (cDenominator / denominator)
        return FractionNumber(cNumerator, cDenominator)
    }

    override fun div(a: INumber): INumber {
        return simplify(
            FractionNumber(
                numerator * (a as FractionNumber).denominator,
                denominator * a.numerator
            )
        )
    }

    override fun times(a: INumber): INumber {
        return simplify(
            FractionNumber(
                numerator * (a as FractionNumber).numerator,
                denominator * a.denominator
            )
        )
    }

    private fun simplify(c: FractionNumber): FractionNumber {
        if (c.numerator == 0L) {
            c.denominator = 0
            return c
        }
        val d: Long = gcd(c.denominator, c.numerator)
        c.numerator /= d
        c.denominator /= d
        return c
    }

    override fun SetString(str: String) {
        TODO("Not yet implemented")
    }

    override fun toString(): String {
        return "$numerator/$denominator"
    }

    private fun gcd(a: Long, b: Long): Long {
        return if (b == 0L) a else gcd(b, a % b)
    }

    private fun abs(a: Long): Long {
        return if (a < 0) a * -1 else a
    }

    private fun lcm(a: Long, b: Long): Long {
        return a * b / gcd(a, b)
    }
}