package com.example.calculator.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
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
        try {
            checkSqr()
            when (_editorStateFlow.value) {
                is PNumberEditor -> {
                    calculatePNumber()
                }
                is FractionNumberEditor -> {
                    calculateFractionNumber()
                }
            }
        } catch (e: Exception) {
            println(e)
        }
    }

    private fun checkSqr(): Boolean {
        if (_editorStateFlow.value.expression.value.first() == '√') {
            lateinit var leftOperand: INumber
            _editorStateFlow.value.let {
                when (it) {
                    is PNumberEditor -> {
                        leftOperand = PNumber(
                            it.expression.value.substring(1),
                            it.base,
                            it.acc()
                        )
                    }
                    is FractionNumberEditor -> {
                        val slices = it.expression.value.substring(1).split(it.delimiter)
                        leftOperand = FractionNumber(
                            slices.getOrElse(0) { "0" }.toLong(),
                            slices.getOrElse(1) { "0" }.toLong(),
                        )
                    }
                }
            }
            _editorStateFlow.value.setValue(
                processor.calculate(leftOperand, leftOperand, '√')
            )
            return true
        }
        return false
    }

    private fun calculateFractionNumber() {
        _editorStateFlow.value.expression.value.apply {
            var splitIndex = 0
            forEachIndexed { index, c ->
                if (c.toString().matches(OPERATORS_FRACTION)) {
                    splitIndex = index
                }
            }
            if (splitIndex != 0) {
                val leftOperand = substring(0 until splitIndex).split('/')
                val rightOperand = substring(splitIndex + 1).split('/')
                val operator = getOrElse(splitIndex) { '+' }
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
    }

    private fun calculatePNumber() {
        (_editorStateFlow.value as PNumberEditor).apply {
            val slices = expression.value.split(OPERATORS)
            val operator = expression.value.find { it.toString().matches(OPERATORS) }
            calculate(
                PNumber(slices.getOrElse(0) { "0" }, this.base, acc()),
                PNumber(slices.getOrElse(1) { "0" }, this.base, acc()),
                operator
            )
        }

    }

    private fun calculate(leftOperand: INumber, rightOperand: INumber, operator: Char?) {
        with(_editorStateFlow.value) {
            when (val answer = processor.calculate(
                leftOperand, rightOperand, operator ?: '+'
            )) {
                is PNumber -> {
                    (this as PNumberEditor).setValue(answer, this)
                }
                is FractionNumber -> _editorStateFlow.value.setValue(answer)
            }
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