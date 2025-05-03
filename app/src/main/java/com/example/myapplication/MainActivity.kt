package com.example.myapplication

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.Activity.BaseActivity
import com.example.myapplication.Adapter.KategoriaAdapter
import com.example.myapplication.ViewModel.MainViewModel
import com.example.myapplication.databinding.ActivityMainBinding

class MainActivity : BaseActivity() {
   private val viewModel =MainViewModel()
    private lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)

        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initКатегории()
    }

    private fun initКатегории() {
        binding.progressBarKategoria.visibility=View.VISIBLE
        viewModel.категория.observe(this, Observer {
            binding.viewKategoria.layoutManager=
                LinearLayoutManager(this@MainActivity,LinearLayoutManager.HORIZONTAL,false)
            binding.viewKategoria.adapter=KategoriaAdapter(it)
            binding.progressBarKategoria.visibility=View.GONE
        })
    }
}