package com.example.calculator.domain.useCase

interface INumber {
    operator fun plus(a: INumber): INumber
    operator fun minus(a: INumber): INumber
    operator fun div(a: INumber): INumber
    operator fun times(a: INumber): INumber
    fun squared(): INumber
}