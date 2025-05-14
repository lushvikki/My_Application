package com.example.myapplication.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.Model.FavoriteModel
import com.example.myapplication.R

class FavoriteAdapter(
    private val items: MutableList<FavoriteModel>,
    private val onDeleteClick: (FavoriteModel) -> Unit // Передаем обработчик для удаления
) : RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {

    // ViewHolder для элемента списка
    inner class FavoriteViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.textViewProductName)
        val price: TextView = view.findViewById(R.id.textViewProductPrice)
        val image: ImageView = view.findViewById(R.id.imageViewProduct)
        val deleteButton: ImageView = view.findViewById(R.id.deleteButton) // Кнопка удаления
    }

    // Создание нового ViewHolder для элемента
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.viewholder_favorite, parent, false)
        return FavoriteViewHolder(view)
    }

    // Привязка данных к элементам списка
    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val item = items[position]
        holder.name.text = item.title
        holder.price.text = "${item.price}₽"
        Glide.with(holder.image.context).load(item.picUrl).into(holder.image)

        // Устанавливаем обработчик на кнопку удаления
        holder.deleteButton.setOnClickListener {
            onDeleteClick(item) // Вызываем функцию удаления
        }
    }

    override fun getItemCount(): Int = items.size

    // Метод для удаления товара из списка
    fun removeItem(position: Int) {
        items.removeAt(position)
        notifyItemRemoved(position)
    }
}