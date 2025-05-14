package com.example.myapplication.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.Model.CartModel
import com.example.myapplication.Model.FavoriteModel
import com.example.myapplication.Model.ItemsModel
import com.example.myapplication.databinding.ViewholderListBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class ListAdapter(private val items: MutableList<ItemsModel>) :
    RecyclerView.Adapter<ListAdapter.ItemViewHolder>() {

    class ItemViewHolder(val binding: ViewholderListBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val firebaseUser = FirebaseAuth.getInstance().currentUser
    private val database = FirebaseDatabase.getInstance().reference

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

            iconFav.setOnClickListener {
                val fav = FavoriteModel(item.title, item.price.toInt(), item.picUrl[0])
                firebaseUser?.uid?.let { uid ->
                    database.child("Избранное").child(uid).push().setValue(fav)
                        .addOnSuccessListener {
                            Toast.makeText(holder.itemView.context, "Добавлено в избранное", Toast.LENGTH_SHORT).show()
                        }
                        .addOnFailureListener {
                            Toast.makeText(holder.itemView.context, "Ошибка: ${it.message}", Toast.LENGTH_SHORT).show()
                        }
                }
            }

            iconCart.setOnClickListener {
                val cart = CartModel(item.title, item.price.toInt(), item.picUrl[0])
                firebaseUser?.uid?.let { uid ->
                    database.child("Корзина").child(uid).push().setValue(cart)
                        .addOnSuccessListener {
                            Toast.makeText(holder.itemView.context, "Добавлено в корзину", Toast.LENGTH_SHORT).show()
                        }
                        .addOnFailureListener {
                            Toast.makeText(holder.itemView.context, "Ошибка: ${it.message}", Toast.LENGTH_SHORT).show()
                        }
                }
            }
        }
    }

    override fun getItemCount(): Int = items.size
}
