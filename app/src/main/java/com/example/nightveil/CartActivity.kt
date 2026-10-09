package com.example.nightveil

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nightveil.adapter.CartAdapter
import com.example.nightveil.data.CartRepository
import java.text.NumberFormat
import java.util.Locale

class CartActivity : AppCompatActivity() {

    private lateinit var cartAdapter: CartAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_cart)

        val cartContainer =
            findViewById<LinearLayout>(
                R.id.cartContainer
            )

        val tvTotal =
            findViewById<TextView>(
                R.id.tvCartTotal
            )

        val tvBack =
            findViewById<TextView>(
                R.id.tvCartBack
            )

        val btnCheckout =
            findViewById<Button>(
                R.id.btnCheckout
            )


        // BACK
        tvBack.setOnClickListener {
            finish()
        }


        // RECYCLERVIEW
        val recyclerView =
            RecyclerView(this)

        recyclerView.layoutParams =
            LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )

        recyclerView.layoutManager =
            LinearLayoutManager(this)

        cartContainer.addView(
            recyclerView
        )


        // CART ADAPTER
        cartAdapter = CartAdapter(
            CartRepository.getItems()
        ) {
            updateTotal(tvTotal)
        }

        recyclerView.adapter =
            cartAdapter


        updateTotal(tvTotal)


        // CHECKOUT
        btnCheckout.setOnClickListener {

            if (CartRepository.getItems().isEmpty()) {

                btnCheckout.text =
                    "CART IS EMPTY"

                return@setOnClickListener
            }


            val intent = Intent(
                this,
                CheckoutActivity::class.java
            )

            startActivity(intent)
        }
    }


    override fun onResume() {
        super.onResume()

        val tvTotal =
            findViewById<TextView>(
                R.id.tvCartTotal
            )

        cartAdapter.notifyDataSetChanged()
        findViewById<Button>(R.id.btnCheckout).text = "CHECKOUT"
        updateTotal(tvTotal)
    }


    private fun updateTotal(
        tvTotal: TextView
    ) {

        val total =
            CartRepository.getTotalPrice()

        val formattedTotal =
            NumberFormat
                .getCurrencyInstance(
                    Locale("id", "ID")
                )
                .format(total)
                .replace(",00", "")

        tvTotal.text =
            formattedTotal
    }
}