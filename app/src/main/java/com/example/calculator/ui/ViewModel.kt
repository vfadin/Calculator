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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {
//    var editor: Editor = FractionNumberEditor()
//        private set
    val editor = MutableStateFlow<Editor>(FractionNumberEditor())
    val _editor = editor.asStateFlow()
    private val processor = Processor()
    val lastOperation = processor.lastOperation

    fun onKeyboardClick(char: Char) = editor.value.doEdit(char)

    fun onBsClick() = editor.value.bs()

    fun onCancelClick() = editor.value.clear()

    fun onEqualClick() {
        when (editor.value) {
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
        editor.value.expression.value.forEachIndexed { index, c ->
            if (c.toString().matches(OPERATORS_FRACTION)) {
                splitIndex = index
            }
        }
        if (splitIndex != 0) {
            val leftOperand = editor.value.expression.value.substring(0 until splitIndex).split('/')
            val rightOperand = editor.value.expression.value.substring(splitIndex + 1).split('/')
            val operator = editor.value.expression.value.getOrElse(splitIndex) { '+' }
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
        val slices = editor.value.expression.value.split(OPERATORS)
        val operator = editor.value.expression.value.find { it.toString().matches(OPERATORS) }
        calculate(
            PNumber(slices.getOrElse(0) { "0" }, (editor.value as PNumberEditor).base),
            PNumber(slices.getOrElse(1) { "0" }, (editor.value as PNumberEditor).base),
            operator
        )
    }

    private fun calculate(leftOperand: INumber, rightOperand: INumber, operator: Char?) {
        when (val answer = processor.calculate(
            leftOperand, rightOperand, operator ?: '+'
        )) {
            is PNumber -> (editor.value as PNumberEditor).setValue(answer, (editor.value as PNumberEditor))
            is FractionNumber -> editor.value.setValue(answer)
        }
    }

    fun setFractionEditor() {
        processor.clear()
        editor.value = FractionNumberEditor()
    }

    fun setPNumberEditor() {
        processor.clear()
        editor.value = PNumberEditor()
    }
}