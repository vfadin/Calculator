package com.example.calculator.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.calculator.databinding.ButtonLayoutBinding

open class KeyboardRecyclerViewAdapter :
    RecyclerView.Adapter<KeyboardRecyclerViewAdapter.ViewHolder>() {

    private val datalist = mutableListOf<String>()
    lateinit var listener: OnItemClickListener
    private var buttonHeight = 100

    interface OnItemClickListener {
        fun onItemClick(value: String)
    }

    fun updateData(datalist: List<String>) {
        this.datalist.apply {
            clear()
            addAll(datalist)
        }
    }

    fun setButtonHeight(height: Int) {
        buttonHeight = height
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    inner class ViewHolder(private val binding: ButtonLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: String) {
            binding.root.layoutParams.height = buttonHeight
            with(binding.buttonKeyboard) {
                text = data
                setOnClickListener {
                    listener.onItemClick(data)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ButtonLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount() = datalist.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(datalist.getOrElse(position) { "" })
    }

}