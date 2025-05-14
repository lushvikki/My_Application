package com.example.myapplication.Activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.Adapter.ProfileAdapter
import com.example.myapplication.Model.ProfileModel
import com.example.myapplication.databinding.ActivityProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding
    private val firebaseUser = FirebaseAuth.getInstance().currentUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupProfileInfo()
        // Обработчик кнопки "Назад"
        binding.backBtn.setOnClickListener {
            onBackPressed()
        }

        binding.btnSaveName.setOnClickListener {
            val newName = binding.etName.text.toString().trim()
            if (newName.isNotEmpty()) {
                val updateRequest = UserProfileChangeRequest.Builder()
                    .setDisplayName(newName)
                    .build()

                firebaseUser?.updateProfile(updateRequest)
                    ?.addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(this, "Имя обновлено", Toast.LENGTH_SHORT).show()
                            setupProfileInfo()
                        } else {
                            Toast.makeText(this, "Ошибка: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
            } else {
                Toast.makeText(this, "Введите имя", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnLogout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

    private fun setupProfileInfo() {
        val profileItems = listOf(
            ProfileModel("Имя", firebaseUser?.displayName ?: "Не указано"),
            ProfileModel("Email", firebaseUser?.email ?: "Не указано"),
            ProfileModel("UID", firebaseUser?.uid ?: "Не указано")
        )

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = ProfileAdapter(profileItems)

        // Отображаем имя в поле ввода
        binding.etName.setText(firebaseUser?.displayName ?: "")
    }
}