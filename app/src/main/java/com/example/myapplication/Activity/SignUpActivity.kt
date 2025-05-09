package com.example.myapplication.Activity

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapplication.R
import com.google.firebase.auth.FirebaseAuth

class SignUpActivity : AppCompatActivity() {

    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnSignup: Button
    private lateinit var tvLoginLink: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        // Инициализация элементов UI
        etEmail = findViewById(R.id.et_email)
        etPassword = findViewById(R.id.et_password)
        btnSignup = findViewById(R.id.btn_signup)
        tvLoginLink = findViewById(R.id.tv_login_link)

        // Обработчик кнопки регистрации
        btnSignup.setOnClickListener {
            signUpUser()
        }

        // Переключение на экран входа
        tvLoginLink.setOnClickListener {
            // Переход на экран входа (LoginActivity)
            startActivity(Intent(this, LoginActivity::class.java))
            finish()  // Закрываем текущую активность
        }

        // Применение отступов для системных панелей
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.layout_sign_up)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun signUpUser() {
        val email = etEmail.text.toString().trim()
        val password = etPassword.text.toString().trim()

        // Проверка на пустые поля
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Пожалуйста, заполните все поля", Toast.LENGTH_SHORT).show()
            return
        }

        // Создание пользователя через Firebase Auth
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Регистрация успешна, переходим на главный экран
                    Toast.makeText(this, "Регистрация прошла успешно", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                } else {
                    // Ошибка регистрации
                    Toast.makeText(this, "Ошибка регистрации: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                }
            }
    }

    override fun onStart() {
        super.onStart()
        // Проверка, если пользователь уже авторизован
        val currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser != null) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}
