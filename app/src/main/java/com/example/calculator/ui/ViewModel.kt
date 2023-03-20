package com.example.calculator.ui

import androidx.lifecycle.ViewModel
import com.example.calculator.domain.useCase.Editor
import com.example.calculator.domain.useCase.INumber
import com.example.calculator.domain.useCase.fractionNumber.FractionNumber
import com.example.calculator.domain.useCase.fractionNumber.FractionNumberEditor
import com.example.calculator.domain.useCase.pNumber.PNumber
import com.example.calculator.domain.useCase.pNumber.PNumberEditor
import com.example.calculator.domain.useCase.processor.Processor
import com.example.calculator.utils.Constants.Companion.OPERATORS
import com.example.calculator.utils.Constants.Companion.OPERATORS_FRACTION
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {
    var editor: Editor = FractionNumberEditor()
        private set
    private val processor = Processor()
    val lastOperation = processor.lastOperation

    fun onKeyboardClick(char: Char) = editor.doEdit(char)

    fun onBsClick() = editor.bs()

    fun onCancelClick() = editor.clear()

    fun onEqualClick() {
        when (editor) {
            is PNumberEditor -> {
                calculatePNumber()
            }
            is FractionNumberEditor -> {
                calculateFractionNumber()
            }
        }
    }

    private fun calculateFractionNumber() {
        var splitIndex = 0
        editor.expression.value.forEachIndexed { index, c ->
            if (c.toString().matches(OPERATORS_FRACTION)) {
                splitIndex = index
            }
        }
        if (splitIndex != 0) {
            val leftOperand = editor.expression.value.substring(0 until splitIndex).split('/')
            val rightOperand = editor.expression.value.substring(splitIndex + 1).split('/')
            val operator = editor.expression.value.getOrElse(splitIndex) { '+' }
            calculate(
                FractionNumber(
                    leftOperand.getOrElse(0) { "0" }.toLong(),
                    leftOperand.getOrElse(1) { "0" }.toLong(),
                ),
                FractionNumber(
                    rightOperand.getOrElse(0) { "0" }.toLong(),
                    rightOperand.getOrElse(1) { "0" }.toLong(),
                ),
                operator
            )
        }
    }

    private fun calculatePNumber() {
        val slices = editor.expression.value.split(OPERATORS)
        val operator = editor.expression.value.find { it.toString().matches(OPERATORS) }
        calculate(
            PNumber(slices.getOrElse(0) { "0" }, (editor as PNumberEditor).base),
            PNumber(slices.getOrElse(1) { "0" }, (editor as PNumberEditor).base),
            operator
        )
    }

    private fun calculate(leftOperand: INumber, rightOperand: INumber, operator: Char?) {
        when (val answer = processor.calculate(
            leftOperand, rightOperand, operator ?: '+'
        )) {
            is PNumber -> (editor as PNumberEditor).setValue(answer, (editor as PNumberEditor))
            is FractionNumber -> editor.setValue(answer)
        }
    }

    fun setFractionEditor() {
        editor = FractionNumberEditor()
    }

    fun setPNumberEditor() {
        editor = PNumberEditor()
    }
}