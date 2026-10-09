package com.example.nightveil

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        findViewById<TextView>(R.id.tvProfileBack).setOnClickListener { finish() }

        findViewById<TextView>(R.id.tvMyOrders).setOnClickListener {
            startActivity(Intent(this, OrdersActivity::class.java))
        }

        findViewById<TextView>(R.id.tvMyWishlist).setOnClickListener {
            startActivity(Intent(this, WishlistActivity::class.java))
        }

        findViewById<TextView>(R.id.tvNotifications).setOnClickListener {
            startActivity(Intent(this, NotificationsActivity::class.java))
        }

        findViewById<TextView>(R.id.tvAbout).setOnClickListener {
            startActivity(Intent(this, AboutActivity::class.java))
        }
    }
}
