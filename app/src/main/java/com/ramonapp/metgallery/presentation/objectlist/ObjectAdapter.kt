package com.ramonapp.metgallery.presentation.objectlist

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ramonapp.metgallery.app.databinding.ObjectListItemBinding

class ObjectAdapter :
    ListAdapter<Int, ObjectAdapter.ViewHolder>(DiffCallback()) {

    private var onClick: ((Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), onClick)
    }

    fun setOnItemClick(onClick: (Int) -> Unit) {
        this.onClick = onClick
    }

    class ViewHolder private constructor(
        private val binding: ObjectListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(item: Int, onClick: ((Int) -> Unit)?) {
            binding.idTxt.text = item.toString()
            binding.root.setOnClickListener {
                onClick?.invoke(item)
            }
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = ObjectListItemBinding.inflate(inflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

}

class DiffCallback : DiffUtil.ItemCallback<Int>() {
    override fun areItemsTheSame(oldItem: Int, newItem: Int): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Int, newItem: Int): Boolean {
        return oldItem == newItem
    }

}