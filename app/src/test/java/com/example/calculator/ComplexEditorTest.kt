package com.example.calculator

import com.example.calculator.domain.useCase.complexNumber.*
import org.junit.Assert.*
import org.junit.Test

class ComplexNumberEditorTest {

    @Test
    fun testSetValue() {
        val editor = ComplexNumberEditor()
        val a = ComplexNumber(1.0, 2.0)
        editor.setValue(a)
        assertEquals("1.0+i*2.0", editor.expression.value)
    }

    @Test
    fun testAddDelim() {
        val editor = ComplexNumberEditor()
        assertEquals(".", editor.addDelim())
        assertEquals("0.", editor.expression.value)
        assertEquals(".", editor.addDelim())
        assertEquals("0..", editor.expression.value)
    }

    @Test
    fun testAddOperator() {
        val editor = ComplexNumberEditor()
        assertEquals("+", editor.addOperator('+'))
        assertEquals("0+", editor.expression.value)
        assertEquals("-", editor.addOperator('-'))
        assertEquals("0+-", editor.expression.value)
    }
}
