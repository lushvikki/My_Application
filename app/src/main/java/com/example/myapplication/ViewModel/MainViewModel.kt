package com.example.myapplication.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.Model.ItemsModel
import com.example.myapplication.Model.KategoriaModel
import com.example.myapplication.Repository.MainRepository

class MainViewModel:ViewModel() {
    private val repository=MainRepository()

    val категория:LiveData<MutableList<KategoriaModel>> = repository.loadКатегория()
    val новинки:LiveData<MutableList<ItemsModel>> = repository.loadНовинки()

    fun loadItems(categoryId:String):LiveData<MutableList<ItemsModel>> {
        return repository.loadItems(categoryId)
    }
}