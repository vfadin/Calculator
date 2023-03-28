package com.example.calculator

import com.example.calculator.domain.useCase.fractionNumber.FractionNumberEditor
import org.junit.Assert.*
import org.junit.Test

class FractionNumberEditorTest {

    @Test
    fun testAddDelimWithExistingDelimiter() {
        val editor = FractionNumberEditor()
        editor.setValue("1/2+3/4")
        assertEquals("1/2+3/4", editor.expression.value)
        assertEquals("1/2+3/4", editor.addDelim())
    }

    @Test
    fun testAddDelimWithExistingOperator() {
        val editor = FractionNumberEditor()
        editor.setValue("1+2/3")
        assertEquals("1+2/3", editor.expression.value)
        assertEquals("1+2/3", editor.addDelim())
    }


    @Test
    fun testAddOperatorWithExistingOperator() {
        val editor = FractionNumberEditor()
        editor.setValue("1/2+3/4")
        assertEquals("1/2+3/4", editor.expression.value)
        assertEquals("1/2+3/4", editor.addOperator('*'))
    }

    @Test
    fun testAddOperatorWithExistingDelimiter() {
        val editor = FractionNumberEditor()
        editor.setValue("1/2")
        assertEquals("1/2", editor.expression.value)
        assertEquals("1/2*", editor.addOperator('*'))
    }

    @Test
    fun testAddOperatorWithoutDelimiter() {
        val editor = FractionNumberEditor()
        editor.setValue("3")
        assertEquals("3", editor.addOperator('+'))
        assertEquals("3", editor.addOperator('-'))
        assertEquals("3", editor.addOperator('*'))
    }
}
