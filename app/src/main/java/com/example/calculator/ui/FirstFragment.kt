package com.example.calculator.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.calculator.R
import com.example.calculator.databinding.FragmentFirstBinding
import com.example.calculator.domain.useCase.pNumber.PNumberEditor
import com.google.android.material.slider.Slider
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!
    private val adapter = KeyboardRecyclerViewAdapter()
    private val viewModel by viewModels<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindUi()
        lifecycleScope.launch {
            launch {
                viewModel.lastOperation.collect {
                    binding.textViewLastOperation.text = it
                }
            }
        }
    }

    private fun bindUi() {
        with(binding){
            lifecycleScope.launch {
                viewModel._editor.collect { ed ->
                    launch {
                        ed.expression.collect {
                            binding.textField.setText(it)
                        }
                    }
                    launch {
                        val width = requireContext().applicationContext.resources.displayMetrics.widthPixels
                        val height = (width - 32) / ed.SPAN_COUNT
                        buttonBs.layoutParams.let {
                            it.width = height
                            it.height = height
                        }
                        buttonEqual.apply {
                            layoutParams.let {
                                it.width = height
                                it.height = height
                            }
                            setOnClickListener { viewModel.onEqualClick() }
                        }
                        buttonBs.apply {
                            layoutParams.let {
                                it.width = height
                                it.height = height
                            }
                            setOnClickListener { viewModel.onBsClick() }
                        }
                        buttonCancel.apply {
                            layoutParams.let {
                                it.width = height
                                it.height = height
                            }
                            setOnClickListener { viewModel.onCancelClick() }
                        }
                        if(ed is PNumberEditor) {
                            slider.visibility = VISIBLE
                            slider.addOnSliderTouchListener(object : Slider.OnSliderTouchListener {
                                override fun onStartTrackingTouch(slider: Slider) {
                                    ed.clear()
                                }

                                override fun onStopTrackingTouch(slider: Slider) {
                                    ed.base = slider.value.roundToInt()
                                }
                            })
                        }
                        keyboard.adapter = adapter
                        adapter.setButtonHeight(height)
                        adapter.setOnItemClickListener(
                            object : KeyboardRecyclerViewAdapter.OnItemClickListener {
                                override fun onItemClick(value: String) {
                                    viewModel.onKeyboardClick(value.first())
                                }
                            })
                        keyboard.layoutManager =
                            GridLayoutManager(requireContext(), ed.SPAN_COUNT)
                    }
                    adapter.updateData(ed.keyboardValues)
                }
            }
        }
        with(binding) {
            toolbar.inflateMenu(R.menu.menu_main)
            toolbar.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.action_fraction -> {
                        viewModel.setFractionEditor()
                        true
                    }
                    R.id.action_pnumber -> {
                        viewModel.setPNumberEditor()
                        true
                    }
                    else -> false
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}