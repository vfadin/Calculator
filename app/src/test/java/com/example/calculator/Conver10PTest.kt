package com.example.calculator

import com.example.calculator.domain.useCase.pNumber.Convert10p.Companion.doP
import com.example.calculator.domain.useCase.pNumber.Convert10p.Companion.fltToP
import com.example.calculator.domain.useCase.pNumber.Convert10p.Companion.intToChar
import com.example.calculator.domain.useCase.pNumber.Convert10p.Companion.intToP
import org.junit.Test
import org.junit.Assert.*

class Convert10PTest {

    @Test(expected = AssertionError::class)
    fun testIntToChar() {
        assertEquals("A", intToChar(10))
        intToChar(16)
    }


//    @Test(expected = IllegalArgumentException::class)
    @Test
    fun testIntToP() {
//        intToP(1, 111)
        assertEquals("1101", intToP(13, 2))
        assertEquals("D", intToP(13, 16))
        assertEquals("14", intToP(20, 16))
        assertEquals("10", intToP(16, 16))
        assertEquals("10000", intToP(16, 2))
        assertEquals("1010", intToP(10, 2))
        assertEquals("0", intToP(0, 2))
        assertEquals("1010", intToP(10, 2))
    }

    @Test(expected = IllegalArgumentException::class)
    fun test() {
        fltToP(1.1, 111, 1)
        assertEquals("1111", fltToP(0.9375, 2, 4))
    }

    @Test
    fun testDoP(){
        assertEquals("11.11", doP(3.75, 2, 2))
        assertEquals("2.5", doP(2.5, 10, 1))
        assertEquals("14.0", doP(20.0, 16, 1))
        assertEquals("A.199999999999", doP(10.1, 16, 12))
        assertEquals("1.56", doP(1.73, 8, 2))
        assertEquals("3.11", doP(3.3333333333333335, 4, 2))
    }
}