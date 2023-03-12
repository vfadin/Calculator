package com.example.calculator.domain.useCase.pNumber

import com.example.calculator.domain.useCase.INumber
import com.example.calculator.domain.useCase.pNumber.ConvertP10.Companion.dval

class PNumber(number: String, base: Int) : INumber {
    var number = .0
    var base = 2

    init {
        this.base = base
        this.number = dval(number, base)
    }

    override fun toString(): String {
        return "$number"
    }

    override fun plus(a: INumber): INumber {
        return PNumber((number + (a as PNumber).number).toString(), base)
    }

    override fun minus(a: INumber): INumber {
        return PNumber((number - (a as PNumber).number).toString(), base)
    }

    override fun div(a: INumber): INumber {
        return PNumber((number / (a as PNumber).number).toString(), base)
    }

    override fun times(a: INumber): INumber {
        return PNumber((number * (a as PNumber).number).toString(), base)
    }

    override fun SetString(str: String) {
        TODO("Not yet implemented")
    }
}