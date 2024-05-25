package com.wildan.subs1intermediet.ui.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.wildan.subs1intermediet.data.config.ItemStory
import com.wildan.subs1intermediet.databinding.ItemStoryBinding
import com.wildan.subs1intermediet.ui.Fragment.FragmentStoryDirections

class AdapterStory: ListAdapter<ItemStory, AdapterStory.ListViewHolder>(DIFF_CALLBACK) {
    class ListViewHolder(private val binding: ItemStoryBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ItemStory) {
            with(binding) {
                ivItemPhoto.load(item.photoUrl)
                tvItemName.text = item.name
                tvDescription.text = item.description

                root.setOnClickListener {
                    val navigation = FragmentStoryDirections.actionHomeFragmentToDetailStoryFragment(item)
                    root.findNavController().navigate(navigation)
                }
            }
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: ItemStoryBinding = ItemStoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
       val user = getItem(position)
        holder.bind(user)
    }

    companion object {


        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ItemStory>() {
            override fun areItemsTheSame(oldItem: ItemStory, newItem: ItemStory): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: ItemStory, newItem: ItemStory): Boolean {
                return oldItem == newItem
            }

        }
    }
}