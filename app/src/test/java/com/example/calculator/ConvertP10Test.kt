package com.example.calculator

import com.example.calculator.domain.useCase.pNumber.ConvertP10.Companion.charToInt
import com.example.calculator.domain.useCase.pNumber.ConvertP10.Companion.dval
import org.junit.Assert
import org.junit.Test

class ConvertP10Test {
    @Test(expected = IndexOutOfBoundsException::class)
    fun testCharToInt() {
        charToInt('z')
        Assert.assertEquals(10, charToInt('A'))
        Assert.assertEquals(15, charToInt('F'))
        Assert.assertEquals(5, charToInt('5'))
    }

    @Test(expected = IndexOutOfBoundsException::class)
    fun testCharToIntException() {
        charToInt('G')
    }

    @Test
    fun testDval() {
        Assert.assertEquals(165.875, dval("A5.E", 16), 0.0)
        Assert.assertEquals(8.25, dval("10.2", 8), 0.0)
        Assert.assertEquals(15.5, dval("F.8", 16), 0.0)
        Assert.assertEquals(1.0, dval("1", 2), 0.0)
    }
}



