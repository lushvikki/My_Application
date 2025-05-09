package com.example.myapplication.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.Model.ItemsModel
import com.example.myapplication.databinding.ViewholderNewBinding

class NewAdapter(val items:MutableList<ItemsModel>):
    RecyclerView.Adapter<NewAdapter.Viewholder>(){
    class Viewholder(val binding: ViewholderNewBinding):
    RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewAdapter.Viewholder {
        val binding=ViewholderNewBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return Viewholder(binding)
    }

    override fun onBindViewHolder(holder: NewAdapter.Viewholder, position: Int) {
        holder.binding.textViewProductPrice.text=items[position].price.toString()+" Руб"
        holder.binding.textViewProductName.text=items[position].title

        Glide.with(holder.itemView.context)
            .load(items[position].picUrl[0])
            .into(holder.binding.imageViewProduct)
    }

    override fun getItemCount(): Int =items.size
}