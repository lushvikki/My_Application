package com.example.myapplication.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.Model.KategoriaModel
import com.example.myapplication.Repository.MainRepository

class MainViewModel:ViewModel() {
    private val repository=MainRepository()

    val категория:LiveData<MutableList<KategoriaModel>> = repository.loadКатегория()
}