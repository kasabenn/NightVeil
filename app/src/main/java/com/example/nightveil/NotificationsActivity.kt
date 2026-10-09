package com.example.nightveil

import android.os.Bundle
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class NotificationsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notifications)

        findViewById<TextView>(R.id.tvNotificationsBack).setOnClickListener { finish() }

        val container = findViewById<LinearLayout>(R.id.notificationsContainer)
        addNotification(container, "Welcome to NIGHTVEIL", "Discover gothic fashion and accessories made for the night.")
        addNotification(container, "Wishlist ready", "Tap the heart on any product to save it for later.")
        addNotification(container, "Free to browse", "Explore products, add them to cart, and checkout anytime.")
    }

    private fun addNotification(container: LinearLayout, title: String, message: String) {
        val card = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(18, 18, 18, 18)
            background = getDrawable(R.drawable.bg_search)
        }

        val params = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        ).apply { setMargins(0, 0, 0, 12) }

        val titleView = TextView(this).apply {
            text = title
            textSize = 14f
            setTypeface(null, android.graphics.Typeface.BOLD)
            setTextColor(getColor(R.color.text_primary))
        }

        val messageView = TextView(this).apply {
            text = message
            textSize = 12f
            setTextColor(getColor(R.color.text_secondary))
            setPadding(0, 6, 0, 0)
        }

        card.addView(titleView)
        card.addView(messageView)
        card.isClickable = true
        card.isFocusable = true
        card.setOnClickListener {
            Toast.makeText(this, title, Toast.LENGTH_SHORT).show()
        }
        container.addView(card, params)
    }
}
