package com.example.calculator.ui

import androidx.lifecycle.ViewModel
import com.example.calculator.domain.useCase.pNumber.PNumberEditor
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {
    val pNumberEditor = PNumberEditor()

    init {

    }

    fun onKeyboardClick(char: Char) {
        pNumberEditor.doEdit(char)
    }
}