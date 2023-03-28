package com.example.calculator

import com.example.calculator.domain.useCase.pNumber.PNumber
import com.example.calculator.domain.useCase.pNumber.PNumberEditor

import org.junit.Assert.assertEquals
import org.junit.Test

class PNumberEditorTest {

    @Test
    fun addDigit_should_add_digit_to_expression() {
        val editor = PNumberEditor()
        editor.addDigit(1)
        editor.addDigit(2)
        assertEquals("12", editor.expression.value)
    }

    @Test
    fun addDigit_should_not_add_digit_outside_of_base_range() {
        val editor = PNumberEditor()
        editor.setBase(16)
        editor.addDigit(20)
        assertEquals("0", editor.expression.value)
    }

    @Test
    fun setValue_should_set_expression_to_number_string() {
        val editor = PNumberEditor()
        val number = PNumber("3A", 16, 1)
        editor.setValue(number, editor)
        assertEquals("3A.0", editor.expression.value)
    }

    @Test
    fun setBase_should_update_base_property() {
        val editor = PNumberEditor()
        editor.setBase(10)
        assertEquals(10, editor.base)
    }
    @Test
    fun textAcc() {
        val editor = PNumberEditor()
        editor.setValue("1.0+5.0")
        assertEquals(1, editor.acc())
    }
}
