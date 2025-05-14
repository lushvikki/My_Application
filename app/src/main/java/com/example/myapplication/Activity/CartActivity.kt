package com.example.myapplication.Activity

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Adapter.CartAdapter
import com.example.myapplication.Model.CartModel
import com.example.myapplication.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class CartActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var cartAdapter: CartAdapter
    private lateinit var cartList: MutableList<CartModel>
    private lateinit var totalTextView: TextView
    private lateinit var emptyCartText: TextView
    private val firebaseUser = FirebaseAuth.getInstance().currentUser
    private val database = FirebaseDatabase.getInstance().reference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_cart)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Инициализация Views
        recyclerView = findViewById(R.id.recyclerView)
        totalTextView = findViewById(R.id.textTotal)
        emptyCartText = findViewById(R.id.emptyCartText)
        recyclerView.layoutManager = LinearLayoutManager(this)
        cartList = mutableListOf()

        // Назад
        val backBtn: ImageView = findViewById(R.id.backBtn)
        backBtn.setOnClickListener {
            onBackPressed()
        }

        // Получение данных из Firebase
        firebaseUser?.uid?.let { uid ->
            database.child("Корзина").child(uid).addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    cartList.clear()
                    var total = 0

                    for (cartSnapshot in snapshot.children) {
                        val cartItem = cartSnapshot.getValue(CartModel::class.java)
                        cartItem?.let {
                            cartList.add(it)
                            total += it.price // <-- Подсчёт суммы
                        }
                    }

                    if (cartList.isEmpty()) {
                        emptyCartText.visibility = View.VISIBLE
                        totalTextView.text = "ИТОГО: 0₽"
                    } else {
                        emptyCartText.visibility = View.GONE
                        totalTextView.text = "ИТОГО: ${total}₽"
                    }

                    cartAdapter = CartAdapter(cartList) { item ->
                        deleteItem(item)
                    }
                    recyclerView.adapter = cartAdapter
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(this@CartActivity, "Ошибка чтения данных", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

    private fun deleteItem(item: CartModel) {
        firebaseUser?.uid?.let { uid ->
            val itemRef = database.child("Корзина").child(uid)
                .orderByChild("title")
                .equalTo(item.title).limitToFirst(1)

            itemRef.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (cartSnapshot in snapshot.children) {
                        cartSnapshot.ref.removeValue()
                    }
                    val position = cartList.indexOf(item)
                    cartList.remove(item)
                    cartAdapter.notifyItemRemoved(position)
                    Toast.makeText(this@CartActivity, "Товар удален", Toast.LENGTH_SHORT).show()
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(this@CartActivity, "Ошибка удаления товара", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}

