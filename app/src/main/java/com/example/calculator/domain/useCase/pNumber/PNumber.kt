package com.example.calculator.domain.useCase.pNumber

import com.example.calculator.domain.useCase.INumber
import com.example.calculator.domain.useCase.pNumber.ConvertP10.Companion.dval

class PNumber(number: Double) : INumber {
    var number = .0

    init {
        this.number = number
    }

    override fun toString(): String {
        return "Number: $number"
    }

    override fun plus(a: INumber): INumber {
        return PNumber(number + (a as PNumber).number)
    }

    override fun minus(a: INumber): INumber {
        TODO("Not yet implemented")
    }

    override fun div(a: INumber): INumber {
        TODO("Not yet implemented")
    }

    override fun times(a: INumber): INumber {
        TODO("Not yet implemented")
    }

    override fun SetString(str: String) {
        TODO("Not yet implemented")
    }
}