package com.example.calculator

import com.example.calculator.domain.useCase.pNumber.PNumber
import com.example.calculator.domain.useCase.processor.Processor
import org.junit.Test

class ProcessorTest {
    @Test
    fun testCalculate() {
        val slices = "12.8+14.1".split(Regex("[+\\-*/]"))
        Processor().calculate(PNumber(slices.getOrElse(0) { "0" }, 10),
            PNumber(slices.getOrElse(1) { "0" }, 10), '+')
    }
}