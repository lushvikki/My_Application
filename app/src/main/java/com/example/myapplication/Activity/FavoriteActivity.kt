package com.example.myapplication.Activity

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Adapter.FavoriteAdapter
import com.example.myapplication.Model.FavoriteModel
import com.example.myapplication.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class FavoriteActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var favoriteAdapter: FavoriteAdapter
    private lateinit var favoriteList: MutableList<FavoriteModel>
    private val firebaseUser = FirebaseAuth.getInstance().currentUser
    private val database = FirebaseDatabase.getInstance().reference
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge() // Включение режима Edge-to-Edge
        setContentView(R.layout.activity_favorite) // Установка layout для активности

        // Устанавливаем отступы с учётом системных баров
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Инициализация прогресс-бара
        progressBar = findViewById(R.id.progressBar2)

        // Настроим кнопку назад
        val backBtn: ImageView = findViewById(R.id.back_button) // Находим кнопку по ID
        backBtn.setOnClickListener {
            onBackPressed() // Обработчик нажатия на кнопку: вызываем стандартный метод для возврата
        }

        // Настроим RecyclerView
        recyclerView = findViewById(R.id.listviewfav)
        recyclerView.layoutManager = LinearLayoutManager(this)
        favoriteList = mutableListOf()

        // Инициализируем адаптер с пустым списком
        favoriteAdapter = FavoriteAdapter(favoriteList) { item ->
            // Логика удаления товара из избранного
            deleteFavoriteItem(item)
        }
        recyclerView.adapter = favoriteAdapter

        // Загружаем данные из Firebase
        firebaseUser?.uid?.let { uid ->
            database.child("Избранное").child(uid).addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    progressBar.visibility = View.VISIBLE

                    favoriteList.clear() // Очистить список перед добавлением новых данных
                    for (favoriteSnapshot in snapshot.children) {
                        val favoriteItem = favoriteSnapshot.getValue(FavoriteModel::class.java)
                        favoriteItem?.let { favoriteList.add(it) }
                    }

                    favoriteAdapter.notifyDataSetChanged()

                    // Скрываем прогресс-бар после загрузки
                    progressBar.visibility = View.GONE
                }

                override fun onCancelled(error: DatabaseError) {
                    // Скрываем прогресс-бар в случае ошибки
                    progressBar.visibility = View.GONE
                    Toast.makeText(this@FavoriteActivity, "Ошибка чтения данных", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

    // Функция для удаления товара из Firebase
    private fun deleteFavoriteItem(item: FavoriteModel) {
        firebaseUser?.uid?.let { uid ->
            val itemRef = database.child("Избранное").child(uid).orderByChild("title")
                .equalTo(item.title).limitToFirst(1)

            itemRef.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (favoriteSnapshot in snapshot.children) {
                        favoriteSnapshot.ref.removeValue()  // Удаление из Firebase
                    }
                    // Обновляем локальный список и адаптер
                    val position = favoriteList.indexOf(item)
                    favoriteList.remove(item)
                    favoriteAdapter.notifyItemRemoved(position)
                    Toast.makeText(this@FavoriteActivity, "Товар удален из избранного", Toast.LENGTH_SHORT).show()
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(this@FavoriteActivity, "Ошибка удаления товара", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

    // Переопределяем поведение кнопки "Назад" на устройстве
    override fun onBackPressed() {
        super.onBackPressed() // Стандартное поведение (закрытие текущей активности)
    }
}