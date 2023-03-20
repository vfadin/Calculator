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
    private val _editorStateFlow = MutableStateFlow<Editor>(FractionNumberEditor())
    val editorStateFlow = _editorStateFlow.asStateFlow()
    private val processor = Processor()
    val lastOperation = processor.lastOperation

    fun onKeyboardClick(char: Char) = _editorStateFlow.value.doEdit(char)

    fun onBsClick() = _editorStateFlow.value.bs()

    fun onCancelClick() = _editorStateFlow.value.clear()

    fun onEqualClick() {
        println(213)
        when (_editorStateFlow.value) {
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
        _editorStateFlow.value.expression.value.forEachIndexed { index, c ->
            if (c.toString().matches(OPERATORS_FRACTION)) {
                splitIndex = index
            }
        }
        if (splitIndex != 0) {
            val leftOperand =
                _editorStateFlow.value.expression.value.substring(0 until splitIndex).split('/')
            val rightOperand =
                _editorStateFlow.value.expression.value.substring(splitIndex + 1).split('/')
            val operator = _editorStateFlow.value.expression.value.getOrElse(splitIndex) { '+' }
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
        val slices = _editorStateFlow.value.expression.value.split(OPERATORS)
        val operator =
            _editorStateFlow.value.expression.value.find { it.toString().matches(OPERATORS) }
        calculate(
            PNumber(slices.getOrElse(0) { "0" }, (_editorStateFlow.value as PNumberEditor).base),
            PNumber(slices.getOrElse(1) { "0" }, (_editorStateFlow.value as PNumberEditor).base),
            operator
        )
    }

    private fun calculate(leftOperand: INumber, rightOperand: INumber, operator: Char?) {
        when (val answer = processor.calculate(
            leftOperand, rightOperand, operator ?: '+'
        )) {
            is PNumber -> (_editorStateFlow.value as PNumberEditor).setValue(
                answer,
                (_editorStateFlow.value as PNumberEditor)
            )
            is FractionNumber -> _editorStateFlow.value.setValue(answer)
        }
    }

    fun setFractionEditor() {
        clearLastOperation()
        _editorStateFlow.value = FractionNumberEditor()
    }

    fun setPNumberEditor() {
        clearLastOperation()
        _editorStateFlow.value = PNumberEditor()
    }

    fun clearLastOperation() {
        processor.clear()
    }
}