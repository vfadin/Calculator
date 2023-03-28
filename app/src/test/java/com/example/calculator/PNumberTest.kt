package com.example.calculator

import com.example.calculator.domain.useCase.pNumber.PNumber
import org.junit.Assert
import org.junit.Test


class PNumberTest {

    @Test
    fun testPlus() {
        val a = PNumber("1", 10, 1)
        val b = PNumber("2", 10, 1)
        val c = a + b
        Assert.assertEquals("3.0", c.toString())
    }

    @Test
    fun testMinus() {
        val a = PNumber("1", 10, 1)
        val b = PNumber("2", 10, 1)
        val c = a - b
        Assert.assertEquals("-1.0", c.toString())
    }

    @Test
    fun testTimes() {
        val a = PNumber("1", 10, 1)
        val b = PNumber("2", 10, 1)
        val c = a * b
        Assert.assertEquals("2.0", c.toString())
    }

    @Test
    fun testDiv() {
        val a = PNumber("1", 10, 1)
        val b = PNumber("2", 10, 1)
        val c = a / b
        Assert.assertEquals("0.5", c.toString())
    }

    @Test
    fun testSquared() {
        val a = PNumber("4", 10, 1)
        val b = a.squared()
        Assert.assertEquals("2.0", b.toString())
    }


}