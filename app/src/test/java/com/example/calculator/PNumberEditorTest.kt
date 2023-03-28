package com.example.calculator

package com.example.calculator.domain.useCase.pNumber

import org.junit.Assert.assertEquals
import org.junit.Test

class PNumberEditorTest {

    @Test
    fun `addDigit should add digit to expression`() {
        val editor = PNumberEditor()
        editor.addDigit(1)
        editor.addDigit(2)
        assertEquals("12", editor.expression.value)
    }

    @Test
    fun `addDigit should not add digit outside of base range`() {
        val editor = PNumberEditor()
        editor.setBase(16)
        editor.addDigit(20)
        assertEquals("", editor.expression.value)
    }

    @Test
    fun `setValue should set expression to number string`() {
        val editor = PNumberEditor()
        val number = PNumber("3A", 16)
        editor.setValue(number, editor)
        assertEquals("3A", editor.expression.value)
    }

    @Test
    fun `setBase should update base property`() {
        val editor = PNumberEditor()
        editor.setBase(10)
        assertEquals(10, editor.base)
    }
}
