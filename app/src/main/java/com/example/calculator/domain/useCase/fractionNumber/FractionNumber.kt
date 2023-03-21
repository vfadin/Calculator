package com.example.calculator.domain.useCase.fractionNumber

import com.example.calculator.domain.useCase.INumber
import kotlin.math.pow
import kotlin.math.sqrt

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
        val cDenominator: Long = lcm(a.denominator, denominator)
        val cNumerator: Long =
            a.numerator * (cDenominator / a.denominator) + numerator * (cDenominator / denominator)
        return simplify(FractionNumber(cNumerator, cDenominator))
    }

    override fun minus(a: INumber): INumber {
        if (denominator == 0L) {
            (a as FractionNumber).numerator *= -1
            return a
        }
        if ((a as FractionNumber).denominator == 0L) return this
//        if (denominator == 0L) return a
        val cDenominator: Long = lcm(a.denominator, denominator)
        val cNumerator: Long = numerator * (cDenominator / denominator) -
                a.numerator * (cDenominator / a.denominator)
        return simplify(FractionNumber(cNumerator, cDenominator))
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

    override fun toString(): String {
        if (denominator < 0 && numerator > 0) {
            return "-$numerator/${denominator * -1}"
        }
        if (denominator == 0L || numerator == 0L) {
            return "0"
        }
        if (denominator == 1L) {
            return "$numerator"
        }
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

    override fun squared(): INumber {
        var numerator = numerator.toDouble().pow(0.5).toLong()
        var denominator = denominator.toDouble().pow(0.5).toLong()
        if (numerator * numerator != this.numerator) {
            numerator = this.numerator
        }
        if (denominator * denominator != this.denominator) {
            denominator = this.denominator
        }
        return FractionNumber(numerator, denominator)
    }
}