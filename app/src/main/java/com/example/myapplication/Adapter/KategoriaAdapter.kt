package com.example.myapplication.Adapter

import android.content.Intent
import android.content.res.ColorStateList
import android.os.Looper
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.Activity.ListItemActivity
import com.example.myapplication.Model.KategoriaModel
import com.example.myapplication.R
import com.example.myapplication.databinding.ViewholderKategoriaBinding

class KategoriaAdapter(val items:MutableList<KategoriaModel>)
    : RecyclerView.Adapter<KategoriaAdapter.Viewholder>() {
    inner class Viewholder(val binding: ViewholderKategoriaBinding)
        :RecyclerView.ViewHolder(binding.root)

    private var selectedPosition = -1
    private var lastSelectedPosition= -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KategoriaAdapter.Viewholder {
        val binding = ViewholderKategoriaBinding.inflate(LayoutInflater.from(parent.context)
            ,parent,false)
        return Viewholder(binding)
    }

    override fun onBindViewHolder(holder: Viewholder, position: Int) {
        val item = items[position]
        holder.binding.titleKat.text = item.title

        Glide.with(holder.itemView.context)
            .load(item.picUrl)
            .into(holder.binding.picKat)

        if (selectedPosition == position) {
            holder.binding.picKat.setBackgroundResource(R.drawable.white_but)
            ImageViewCompat.setImageTintList(
                holder.binding.picKat,
                ColorStateList.valueOf(ContextCompat.getColor(holder.itemView.context, R.color.lightPink))
            )
        } else {
            holder.binding.picKat.setBackgroundResource(R.drawable.white_circ_but)
            ImageViewCompat.setImageTintList(
                holder.binding.picKat,
                ColorStateList.valueOf(ContextCompat.getColor(holder.itemView.context, R.color.lightRed))
            )
        }

        holder.binding.root.setOnClickListener {
            val currentPos = holder.adapterPosition
            if (currentPos != RecyclerView.NO_POSITION) {
                val currentItem = items[currentPos]

                lastSelectedPosition = selectedPosition
                selectedPosition = currentPos
                notifyItemChanged(lastSelectedPosition)
                notifyItemChanged(selectedPosition)

                android.os.Handler(Looper.getMainLooper()).postDelayed({
                    val intent = Intent(holder.itemView.context, ListItemActivity::class.java).apply {
                        putExtra("id", currentItem.id.toString())
                        putExtra("title", currentItem.title)
                    }
                    ContextCompat.startActivity(holder.itemView.context, intent, null)
                }, 500)
            }
        }
    }

    override fun getItemCount(): Int =items.size
}