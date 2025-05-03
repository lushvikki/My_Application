package com.example.myapplication.Repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.myapplication.Model.KategoriaModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainRepository {
    private val firebaseDatabase=FirebaseDatabase.getInstance()

    fun loadКатегория():LiveData<MutableList<KategoriaModel>>{
        val категорияLiveData=MutableLiveData<MutableList<KategoriaModel>>()
        val ref=firebaseDatabase.getReference("Категория")

        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val lists= mutableListOf<KategoriaModel>()
                for(chilldSnapschot in snapshot.children){
                    val list=chilldSnapschot.getValue(KategoriaModel::class.java)
                    if(list!=null){
                        lists.add(list)
                    }
                }
                категорияLiveData.value=lists
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
        return категорияLiveData
    }
}