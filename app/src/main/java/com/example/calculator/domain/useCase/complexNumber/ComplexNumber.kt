package com.example.calculator.domain.useCase.complexNumber

import com.example.calculator.domain.useCase.INumber

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
            real + (a as ComplexNumber).real, imaginary + a.imaginary
        )
    }


    override fun minus(a: INumber): INumber {
        return ComplexNumber(real - (a as ComplexNumber).real, imaginary - a.imaginary)
    }

    override fun div(a: INumber): INumber {
        val denom = (a as ComplexNumber).real * a.real + a.imaginary * a.imaginary
        val newReal = (real * a.real + imaginary * a.imaginary) / denom
        val newImaginary = (imaginary * a.real - real * a.imaginary) / denom
        return ComplexNumber(newReal, newImaginary)
    }

    override fun times(a: INumber): INumber {
        val newReal = real * (a as ComplexNumber).real - imaginary * a.imaginary
        val newImaginary = imaginary * a.real + real * a.imaginary
        return ComplexNumber(newReal, newImaginary)
    }

    override fun squared(): INumber {
        val newReal = real * real - imaginary * imaginary
        val newImaginary = 2 * real * imaginary
        return ComplexNumber(newReal, newImaginary)
    }

}