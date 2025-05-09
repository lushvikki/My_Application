package com.example.myapplication.Activity

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.Adapter.ListAdapter
import com.example.myapplication.R
import com.example.myapplication.ViewModel.MainViewModel
import com.example.myapplication.databinding.ActivityListItemBinding

class ListItemActivity : AppCompatActivity() {
    lateinit var binding: ActivityListItemBinding
    private var viewModel=MainViewModel()
    private var id:String=""
    private var title:String=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityListItemBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getBundle()
        initList()

    }
    private fun initList(){
        binding.apply {
            progressBar2.visibility=View.VISIBLE
           viewModel.loadItems(id).observe(this@ListItemActivity, Observer {
               listview.layoutManager = LinearLayoutManager(this@ListItemActivity, LinearLayoutManager.VERTICAL,false)
               listview.adapter = ListAdapter(it)
               progressBar2.visibility=View.GONE
           })
            backButt.setOnClickListener { finish() }
        }
    }

    private fun getBundle(){
        id=intent.getStringExtra("id")!!
        title=intent.getStringExtra("title")!!
        binding.kategoriaTxt.text=title
    }
}