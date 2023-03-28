package com.example.calculator.domain.useCase.complexNumber

import com.example.calculator.domain.useCase.INumber
import kotlin.math.*


class ComplexNumber(real: Double, imaginary: Double) : INumber {

    var real = 0.0
        private set
    var imaginary = 0.0
        private set

    init {
        this.real = real
        this.imaginary = imaginary
    }

    override fun plus(a: INumber): INumber {
        return ComplexNumber(
            ((real + (a as ComplexNumber).real) * 1000).roundToInt() / 1000.0,
            ((imaginary + a.imaginary) * 1000).roundToInt() / 1000.0
        )
    }


    override fun minus(a: INumber): INumber {
        return ComplexNumber(
            ((real - (a as ComplexNumber).real) * 1000).roundToInt() / 1000.0,
            ((imaginary - a.imaginary) * 1000).roundToInt() / 1000.0
        )
    }

    override fun div(a: INumber): INumber {
        val denom = (a as ComplexNumber).real * a.real + a.imaginary * a.imaginary
        val newReal = (real * a.real + imaginary * a.imaginary) / denom
        val newImaginary = (imaginary * a.real - real * a.imaginary) / denom
        return ComplexNumber(
            (newReal * 1000).roundToInt() / 1000.0,
            (newImaginary * 1000).roundToInt() / 1000.0
        )
    }

    override fun times(a: INumber): INumber {
        val newReal = real * (a as ComplexNumber).real - imaginary * a.imaginary
        val newImaginary = imaginary * a.real + real * a.imaginary
        return ComplexNumber(
            (newReal * 1000).roundToInt() / 1000.0,
            (newImaginary * 1000).roundToInt() / 1000.0
        )
    }

    fun abs(): Double {
        return sqrt(real * real + imaginary * imaginary)
    }

    override fun squared(): INumber {
        val modulus = abs()
        val argument = atan2(imaginary, real)
        val sqrtModulus = sqrt(modulus)
        val halfAngle = argument / 2.0
        val newReal = sqrtModulus * cos(halfAngle)
        val newImaginary = sqrtModulus * sin(halfAngle)
        return ComplexNumber(
            (newReal * 1000).roundToInt() / 1000.0,
            (newImaginary * 1000).roundToInt() / 1000.0
        )
    }

    override fun toString(): String {
        var result = real.toString()
        result += if (imaginary < 0) {
            "-i*" + (imaginary * -1).toString()
        } else {
            "+i*$imaginary"
        }
        return result
    }
}