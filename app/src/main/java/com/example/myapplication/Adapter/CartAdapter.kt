package com.example.myapplication.Adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.Model.CartModel
import com.example.myapplication.Model.ItemsModel
import com.example.myapplication.R
import android.view.LayoutInflater

class CartAdapter(private val items: List<CartModel>) :
    RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    inner class CartViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.textViewProductName)
        val price: TextView = view.findViewById(R.id.textViewProductPrice)
        val image: ImageView = view.findViewById(R.id.imageViewProduct)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_cart, parent, false)
        return CartViewHolder(view)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val item = items[position]
        holder.name.text = item.title
        holder.price.text = "${item.price}₽"
        Glide.with(holder.image.context).load(item.picUrl).into(holder.image)
    }

    override fun getItemCount(): Int = items.size
}
