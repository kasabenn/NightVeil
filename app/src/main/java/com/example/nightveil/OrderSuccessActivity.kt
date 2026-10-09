package com.example.nightveil

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class OrderSuccessActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(
            R.layout.activity_order_success
        )

        val tvOrderId =
            findViewById<TextView>(
                R.id.tvOrderId
            )

        val btnContinue =
            findViewById<Button>(
                R.id.btnContinueShopping
            )


        val orderId =
            intent.getStringExtra(
                "ORDER_ID"
            )


        if (orderId != null) {

            tvOrderId.text =
                "Order #$orderId"
        }


        btnContinue.setOnClickListener {

            val intent =
                Intent(
                    this,
                    MainActivity::class.java
                )

            intent.flags =
                Intent.FLAG_ACTIVITY_CLEAR_TOP or
                        Intent.FLAG_ACTIVITY_SINGLE_TOP

            startActivity(intent)

            finish()
        }
    }
}