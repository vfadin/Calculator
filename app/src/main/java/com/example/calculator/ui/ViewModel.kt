package com.example.calculator.ui

import androidx.lifecycle.ViewModel
import com.example.calculator.domain.useCase.pNumber.Convert10p.Companion.doP
import com.example.calculator.domain.useCase.pNumber.PNumber
import com.example.calculator.domain.useCase.pNumber.PNumberEditor
import com.example.calculator.domain.useCase.processor.Processor
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {
    val pNumberEditor = PNumberEditor()
    private val processor = Processor()
    val lastOperation = processor.lastOperation

    init {

    }

    fun onKeyboardClick(char: Char) {
        pNumberEditor.doEdit(char)
    }

    fun onBsClick() {
        pNumberEditor.bs()
    }

    fun onCancelClick() {
        pNumberEditor.clear()
    }

    fun onEqualClick() {
        val slices = pNumberEditor.expression.value.split(Regex("[+\\-*/]"))
        when (val answer = processor.calculate(
            PNumber(slices.getOrElse(0) { "0" }, pNumberEditor.base),
            PNumber(slices.getOrElse(1) { "0" }, pNumberEditor.base),
            '+'
        )) {
            is PNumber -> pNumberEditor.setValue(doP(answer.number, pNumberEditor.base, pNumberEditor.acc()))
        }
    }
}