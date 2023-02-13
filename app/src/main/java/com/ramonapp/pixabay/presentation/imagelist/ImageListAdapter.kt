package com.ramonapp.pixabay.presentation.imagelist

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.ramonapp.pixabay.app.databinding.ImageListItemBinding
import com.ramonapp.pixabay.domain.model.ImageModel

class ImageListAdapter :
    ListAdapter<ImageModel, ImageListAdapter.ViewHolder>(ImageModelDiffCallback()) {

    private var onClick: ((ImageModel) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), onClick)
    }

    fun setOnItemClick(onClick: (ImageModel) -> Unit) {
        this.onClick = onClick
    }

    class ViewHolder private constructor(
        private val binding: ImageListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(item: ImageModel, onClick: ((ImageModel) -> Unit)?) {
            binding.imageTitleTxt.text = "User:  ${item.user}"
            binding.imageTagTxt.text = "Tags:  ${item.tags}"
            binding.imageImg.load(item.thumbnailUrl)
            binding.root.setOnClickListener {
                onClick?.invoke(item)
            }
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = ImageListItemBinding.inflate(inflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

}

class ImageModelDiffCallback : DiffUtil.ItemCallback<ImageModel>() {
    override fun areItemsTheSame(oldItem: ImageModel, newItem: ImageModel): Boolean {
        return oldItem.thumbnailUrl == newItem.thumbnailUrl
    }

    override fun areContentsTheSame(oldItem: ImageModel, newItem: ImageModel): Boolean {
        return oldItem == newItem
    }

}