package com.example.calculator.ui

import androidx.lifecycle.ViewModel
import com.example.calculator.domain.useCase.Editor
import com.example.calculator.domain.useCase.INumber
import com.example.calculator.domain.useCase.complexNumber.ComplexNumber
import com.example.calculator.domain.useCase.complexNumber.ComplexNumberEditor
import com.example.calculator.domain.useCase.fractionNumber.FractionNumber
import com.example.calculator.domain.useCase.fractionNumber.FractionNumberEditor
import com.example.calculator.domain.useCase.pNumber.PNumber
import com.example.calculator.domain.useCase.pNumber.PNumberEditor
import com.example.calculator.domain.useCase.processor.Processor
import com.example.calculator.utils.Constants.Companion.COMPLEX_NUMBER_PATTERN
import com.example.calculator.utils.Constants.Companion.OPERATORS
import com.example.calculator.utils.Constants.Companion.OPERATORS_FRACTION
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {
    private val _editorStateFlow = MutableStateFlow<Editor>(ComplexNumberEditor())
    val editorStateFlow = _editorStateFlow.asStateFlow()
    private val _errorStateFlow = MutableStateFlow("")
    val errorStateFlow = _errorStateFlow.asStateFlow()
    private val processor = Processor()
    val lastOperation = processor.lastOperation

    fun onKeyboardClick(char: Char) = _editorStateFlow.value.doEdit(char)

    fun onBsClick() = _editorStateFlow.value.bs()

    fun onCancelClick() {
        clearLastOperation()
        _editorStateFlow.value.clear()
    }

    fun onEqualClick() {
        _errorStateFlow.value = ""
        try {
            if (!checkSqr())
                when (_editorStateFlow.value) {
                    is PNumberEditor -> {
                        calculatePNumber()
                    }
                    is FractionNumberEditor -> {
                        calculateFractionNumber()
                    }
                    is ComplexNumberEditor -> {
                        calculateComplexNumber()
                    }
                }
        } catch (e: Exception) {
            _errorStateFlow.value = e.message ?: ""
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
                            slices.getOrElse(1) { "1" }.toLong(),
                        )
                    }
                    is ComplexNumberEditor -> {
                        val matchResult = COMPLEX_NUMBER_PATTERN.find(it.expression.value)
                        var realPartFirst = 0.0
                        var imaginaryPartFirst = 0.0
                        if (matchResult != null) {
                            realPartFirst = matchResult.groupValues[1].toDouble()
                            imaginaryPartFirst =
                                (matchResult.groupValues[2] + matchResult.groupValues[3]).toDouble()
                            println("Real part: $realPartFirst")
                            println("Imaginary part: $imaginaryPartFirst")
                        } else {
                            throw IllegalArgumentException("Используйте выражение в формате √0.0+i*0.0")
                        }
                        leftOperand = ComplexNumber(realPartFirst, imaginaryPartFirst)
                    }
                }
            }
            val answer = processor.calculate(leftOperand, leftOperand, '√')
            if (answer is FractionNumber) {
                fractionNumberAnswer(answer, leftOperand)
            } else if (answer is ComplexNumber) {
                _editorStateFlow.value.setValue(answer)
            } else {
                _editorStateFlow.value.setValue(answer)
            }
            return true
        }
        return false
    }

    private fun fractionNumberAnswer(answer: FractionNumber, leftOperand: INumber) {
        var stringAnswer = ""
        if (answer.numerator == (leftOperand as FractionNumber).numerator) {
            if (answer.numerator != 1L) stringAnswer += "√"
        }
        stringAnswer += "${answer.numerator}/"
        if (answer.denominator == 1L) {
            stringAnswer = stringAnswer.substring(0 until stringAnswer.lastIndex)
            _editorStateFlow.value.setValue(stringAnswer)
            return
        }
        if (answer.denominator == leftOperand.denominator) {
            stringAnswer += "√"
        }
        stringAnswer += "${answer.denominator}"
        _editorStateFlow.value.setValue(stringAnswer)
    }

    private fun calculateComplexNumber() {
        _editorStateFlow.value.expression.value.apply {
            var splitIndex = 0
            var counter = 0
            var find = false
            var realPartFirst = 0.0
            var imaginaryPartFirst = 0.0
            var realPartSecond = 0.0
            var imaginaryPartSecond = 0.0
            forEachIndexed { index, c ->
                if (c.toString().matches(OPERATORS)) {
                    ++counter
                }
                if (counter == 3 && !find) {
                    find = true
                    splitIndex = index
                }
            }
            val operator = getOrElse(splitIndex) { '+' }
            val firstExpression = substring(0, splitIndex)
            val secondExpression = substring(splitIndex + 1)
            var matchResult = COMPLEX_NUMBER_PATTERN.find(firstExpression)
            if (matchResult != null) {
                realPartFirst = matchResult.groupValues[1].toDouble()
                imaginaryPartFirst =
                    (matchResult.groupValues[2] + matchResult.groupValues[3]).toDouble()
                println("Real part: $realPartFirst")
                println("Imaginary part: $imaginaryPartFirst")
            } else {
                throw IllegalArgumentException("Используйте выражение в формате 0.0+i*0.0")
            }
            matchResult = COMPLEX_NUMBER_PATTERN.find(secondExpression)
            if (matchResult != null) {
                realPartSecond = matchResult.groupValues[1].toDouble()
                imaginaryPartSecond =
                    (matchResult.groupValues[2] + matchResult.groupValues[3]).toDouble()
                println("Real part: $realPartSecond")
                println("Imaginary part: $imaginaryPartSecond")
            } else {
                throw IllegalArgumentException("Используйте выражение в формате 0.0+i*0.0")
            }
            calculate(
                ComplexNumber(
                    realPartFirst,
                    imaginaryPartFirst,
                ),
                ComplexNumber(
                    realPartSecond,
                    imaginaryPartSecond,
                ),
                operator
            )
        }
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
                is ComplexNumber -> {
                    _editorStateFlow.value.setComplex(answer)
                }
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

    fun setComplexEditor() {
        clearLastOperation()
        _editorStateFlow.value = ComplexNumberEditor()
    }

    fun clearLastOperation() {
        processor.clear()
    }
}