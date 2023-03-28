package com.example.calculator

import com.example.calculator.domain.useCase.fractionNumber.FractionNumber
import org.junit.Assert
import org.junit.Test


class FractionTest {

    @Test
    fun testPlus() {
        val a = FractionNumber(1, 2)
        val b = FractionNumber(1, 3)
        val c = a + b
        Assert.assertEquals(5, (c as FractionNumber).numerator)
        Assert.assertEquals(6, c.denominator)
    }

    @Test
    fun testMinus() {
        val a = FractionNumber(1, 2)
        val b = FractionNumber(1, 3)
        val c = a - b
        Assert.assertEquals(1, (c as FractionNumber).numerator)
        Assert.assertEquals(6, c.denominator)
    }

    @Test
    fun testTimes() {
        val a = FractionNumber(1, 2)
        val b = FractionNumber(1, 3)
        val c = a * b
        Assert.assertEquals(1, (c as FractionNumber).numerator)
        Assert.assertEquals(6, c.denominator)
    }

    @Test
    fun testDiv() {
        val a = FractionNumber(1, 2)
        val b = FractionNumber(1, 3)
        val c = a / b
        Assert.assertEquals(3, (c as FractionNumber).numerator)
        Assert.assertEquals(2, c.denominator)
    }

    @Test
    fun testSquared() {
        val a = FractionNumber(1, 4)
        val b = a.squared()
        Assert.assertEquals(1L, (b as FractionNumber).numerator)
        Assert.assertEquals(2L, b.denominator)
    }
}