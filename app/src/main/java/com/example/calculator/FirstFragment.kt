package com.example.calculator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.calculator.databinding.FragmentFirstBinding

class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!
    private val adapter = KeyboardRecyclerViewAdapter()
    private val SPAN_COUNT = 4

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
    }

    private fun bindUi() {
        val width = requireContext().applicationContext.resources.displayMetrics.widthPixels
        with(binding) {
            keyboard.adapter = adapter
            keyboard.layoutManager = GridLayoutManager(requireContext(), SPAN_COUNT)
            adapter.updateData(listOf(
                "7", "8", "9", "/", "4", "5", "6", "*", "1", "2", "3", "-", "0", ".", "=", "+"))
            adapter.setButtonHeight((width - 32) / SPAN_COUNT)
            adapter.setOnItemClickListener(
                object : KeyboardRecyclerViewAdapter.OnItemClickListener {
                    override fun onItemClick(position: String) {
                        println(position)
                    }
                })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}