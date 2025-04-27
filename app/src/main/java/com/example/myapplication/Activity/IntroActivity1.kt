package com.example.myapplication.Activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivityIntroBinding
import com.example.myapplication.MainActivity

class IntroActivity1 : AppCompatActivity() {

    private lateinit var binding: ActivityIntroBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Инициализация binding через DataBinding
        binding = ActivityIntroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Установка обработчика клика для кнопки
        binding.startBtn.setOnClickListener {
            startActivity(Intent(this@IntroActivity1, MainActivity::class.java))
        }
    }
}