package com.example.myapplication.Activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.Adapter.KategoriaAdapter
import com.example.myapplication.Adapter.NewAdapter
import com.example.myapplication.R
import com.example.myapplication.ViewModel.MainViewModel
import com.example.myapplication.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import android.view.MenuItem

class MainActivity : BaseActivity() {
    private val viewModel = MainViewModel()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initКатегории()
        initНовинки()

        // Теперь правильно привязываем setOnItemSelectedListener
        binding.bottomNavigation.setOnItemSelectedListener { item: MenuItem ->
            when (item.itemId) {
                R.id.nav_cart -> {
                    startActivity(Intent(this, CartActivity::class.java))
                    true
                }
                R.id.nav_favorites -> {
                    startActivity(Intent(this, FavoriteActivity::class.java))
                    true
                }
                R.id.nav_profile -> {
                    startActivity(Intent(this, ProfileActivity::class.java))
                    true
                }
                else -> false
            }
        }
    }

    private fun initНовинки() {
        binding.progressBarNew.visibility = View.VISIBLE
        viewModel.новинки.observe(this, Observer {
            binding.viewNew.layoutManager =
                LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
            binding.viewNew.adapter = NewAdapter(it)
            binding.progressBarNew.visibility = View.GONE
        })
    }

    private fun initКатегории() {
        binding.progressBarKategoria.visibility = View.VISIBLE
        viewModel.категория.observe(this, Observer {
            binding.viewKategoria.layoutManager =
                LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
            binding.viewKategoria.adapter = KategoriaAdapter(it)
            binding.progressBarKategoria.visibility = View.GONE
        })
    }
}