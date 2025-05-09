package com.example.myapplication.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.Model.ItemsModel
import com.example.myapplication.databinding.ViewholderListBinding

class ListAdapter(private val items: MutableList<ItemsModel>) :
    RecyclerView.Adapter<ListAdapter.ItemViewHolder>() {

    class ItemViewHolder(val binding: ViewholderListBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = ViewholderListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = items[position]
        holder.binding.apply {
            textViewProductName.text = item.title
            textViewProductPrice.text = "${item.price} РУБ"
            Glide.with(holder.itemView.context)
                .load(item.picUrl[0])
                .into(imageViewProduct)
        }
    }

    override fun getItemCount(): Int = items.size
}
