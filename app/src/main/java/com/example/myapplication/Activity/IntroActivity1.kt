package com.example.myapplication.Activity

import android.content.Intent
import android.os.Bundle
import com.example.myapplication.databinding.ActivityIntroBinding

class IntroActivity1 : BaseActivity() {

    private lateinit var binding: ActivityIntroBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Инициализация binding через DataBinding
        binding = ActivityIntroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Установка обработчика клика для кнопки
        binding.startBtn.setOnClickListener {
            startActivity(Intent(this@IntroActivity1, SignUpActivity::class.java))
        }
    }
}