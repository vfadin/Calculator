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
import kotlinx.coroutines.launch

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
            viewModel.pNumberEditor.expression.collect {
                binding.textField.setText(it)
            }
        }
    }

    private fun bindUi() {
        val width = requireContext().applicationContext.resources.displayMetrics.widthPixels
        with(binding) {
            keyboard.adapter = adapter
            keyboard.layoutManager = GridLayoutManager(requireContext(), SPAN_COUNT)
            adapter.setButtonHeight((width - 32) / SPAN_COUNT)
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