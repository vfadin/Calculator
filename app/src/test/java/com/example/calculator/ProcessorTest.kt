package com.example.calculator

import com.example.calculator.domain.useCase.processor.Processor
import org.junit.Test

class ProcessorTest {
    @Test
    fun testCalculate() {
        Processor().calculate("12")
    }
}