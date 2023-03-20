package com.example.calculator.domain.useCase.pNumber

import com.example.calculator.domain.useCase.INumber
import com.example.calculator.domain.useCase.pNumber.Convert10p.Companion.doP
import com.example.calculator.domain.useCase.pNumber.ConvertP10.Companion.dval

class PNumber(number: String, base: Int, acc: Int) : INumber {
    var base = 2
    private var acc = 0
    var number = .0
        private set

    init {
        this.base = base
        this.number = dval(number, base)
        this.acc = acc
    }

    override fun toString(): String {
        return doP(number, base, acc)
    }

    override fun plus(a: INumber): INumber {
        return PNumber(doP(number + (a as PNumber).number, 16, acc), base, acc)
    }

    override fun minus(a: INumber): INumber {
        return PNumber(doP(number - (a as PNumber).number, 16, acc), base, acc)
    }

    override fun div(a: INumber): INumber {
        return PNumber(doP(number / (a as PNumber).number, 16, acc), base, acc)
    }

    override fun times(a: INumber): INumber {
        return PNumber(doP(number * (a as PNumber).number, 16, acc), base, acc)
    }
}