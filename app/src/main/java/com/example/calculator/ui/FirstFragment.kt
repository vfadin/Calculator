package com.example.calculator.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.calculator.databinding.FragmentFirstBinding
import com.google.android.material.slider.Slider
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!
    private val adapter = KeyboardRecyclerViewAdapter()
    private val SPAN_COUNT = 4
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
            viewModel.pNumberEditor.expression.collect {
                binding.textField.setText(it)
            }
        }
    }

    private fun bindUi() {
        val width = requireContext().applicationContext.resources.displayMetrics.widthPixels
        val height = (width - 32) / SPAN_COUNT
        with(binding) {
            slider.addOnSliderTouchListener(object : Slider.OnSliderTouchListener {
                override fun onStartTrackingTouch(slider: Slider) {
                    viewModel.pNumberEditor.clear()
                }

                override fun onStopTrackingTouch(slider: Slider) {
                    viewModel.pNumberEditor.base = slider.value.roundToInt()
                }
            })
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
            keyboard.adapter = adapter
            keyboard.layoutManager = GridLayoutManager(requireContext(), SPAN_COUNT)
            adapter.setButtonHeight(height)
            adapter.updateData(viewModel.pNumberEditor.keyboardValues)
            adapter.setOnItemClickListener(
                object : KeyboardRecyclerViewAdapter.OnItemClickListener {
                    override fun onItemClick(value: String) {
                        viewModel.onKeyboardClick(value.first())
                    }
                })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}