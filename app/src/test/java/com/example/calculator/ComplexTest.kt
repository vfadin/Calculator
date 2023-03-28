package com.example.calculator

import com.example.calculator.domain.useCase.complexNumber.ComplexNumber
import org.junit.Assert
import org.junit.Test


class ComplexTest {
    @Test
    fun testPlus() {
        val a = ComplexNumber(1.0, 2.0)
        val b = ComplexNumber(3.0, 4.0)
        val c = a + b
        Assert.assertEquals(4.0, (c as ComplexNumber).real, 0.0)
        Assert.assertEquals(6.0, c.imaginary, 0.0)
    }

    @Test
    fun testMinus() {
        val a = ComplexNumber(1.0, 2.0)
        val b = ComplexNumber(3.0, 4.0)
        val c = a - b
        Assert.assertEquals(-2.0, (c as ComplexNumber).real, 0.0)
        Assert.assertEquals(-2.0, c.imaginary, 0.0)
    }

    @Test
    fun testTimes() {
        val a = ComplexNumber(1.0, 2.0)
        val b = ComplexNumber(3.0, 4.0)
        val c = a * b
        Assert.assertEquals(-5.0, (c as ComplexNumber).real, 0.0)
        Assert.assertEquals(10.0, c.imaginary, 0.0)
    }

    @Test
    fun testDiv() {
        val a = ComplexNumber(1.0, 2.0)
        val b = ComplexNumber(3.0, 4.0)
        val c = a / b
        Assert.assertEquals(0.44, (c as ComplexNumber).real, 0.01)
        Assert.assertEquals(0.08, c.imaginary, 0.01)
    }

    @Test
    fun testAbs() {
        val a = ComplexNumber(3.0, 4.0)
        Assert.assertEquals(5.0, a.abs(), 0.0)
    }

    @Test
    fun testSquared() {
        val a = ComplexNumber(3.0, 4.0)
        val b = a.squared()
        Assert.assertEquals(2.0, (b as ComplexNumber).real, 1.0)
    }

}