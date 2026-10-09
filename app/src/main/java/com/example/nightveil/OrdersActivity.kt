package com.example.nightveil

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.nightveil.data.OrderRepository
import java.text.NumberFormat
import java.util.Locale

class OrdersActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_orders)

        val tvBack =
            findViewById<TextView>(
                R.id.tvOrdersBack
            )

        val ordersContainer =
            findViewById<LinearLayout>(
                R.id.ordersContainer
            )


        // BACK
        tvBack.setOnClickListener {
            finish()
        }


        // LOAD ORDERS
        val orders =
            OrderRepository.getOrders()


        if (orders.isEmpty()) {

            val emptyText =
                TextView(this)

            emptyText.text =
                "No orders yet.\n\nStart shopping at NIGHTVEIL."

            emptyText.gravity =
                android.view.Gravity.CENTER

            emptyText.setTextColor(
                getColor(R.color.text_secondary)
            )

            emptyText.textSize = 14f

            emptyText.setPadding(
                20,
                100,
                20,
                100
            )

            ordersContainer.addView(
                emptyText
            )

            return
        }


        // DISPLAY ORDERS
        for (order in orders) {

            val card =
                LinearLayout(this)

            card.orientation =
                LinearLayout.VERTICAL

            card.setPadding(
                18,
                18,
                18,
                18
            )

            card.background =
                getDrawable(
                    R.drawable.bg_search
                )


            val cardParams =
                LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )

            cardParams.setMargins(
                0,
                0,
                0,
                14
            )


            // ORDER ID

            val tvOrderId =
                TextView(this)

            tvOrderId.text =
                "ORDER #${order.orderId}"

            tvOrderId.setTextColor(
                getColor(R.color.burgundy)
            )

            tvOrderId.textSize = 13f

            tvOrderId.setTypeface(
                null,
                android.graphics.Typeface.BOLD
            )


            // DATE

            val tvDate =
                TextView(this)

            tvDate.text =
                order.date

            tvDate.setTextColor(
                getColor(R.color.text_secondary)
            )

            tvDate.textSize = 11f

            tvDate.setPadding(
                0,
                5,
                0,
                10
            )


            // PAYMENT

            val tvPayment =
                TextView(this)

            tvPayment.text =
                "Payment: ${order.paymentMethod}"

            tvPayment.setTextColor(
                getColor(R.color.text_primary)
            )

            tvPayment.textSize = 12f


            // ADDRESS

            val tvAddress =
                TextView(this)

            tvAddress.text =
                "Address: ${order.address}"

            tvAddress.setTextColor(
                getColor(R.color.text_secondary)
            )

            tvAddress.textSize = 11f

            tvAddress.setPadding(
                0,
                6,
                0,
                12
            )


            // TOTAL

            val tvTotal =
                TextView(this)

            tvTotal.text =
                "Total  ${
                    formatPrice(order.total)
                }"

            tvTotal.setTextColor(
                getColor(R.color.text_primary)
            )

            tvTotal.textSize = 14f

            tvTotal.setTypeface(
                null,
                android.graphics.Typeface.BOLD
            )


            card.addView(tvOrderId)
            card.addView(tvDate)
            card.addView(tvPayment)
            card.addView(tvAddress)
            card.addView(tvTotal)

            card.isClickable = true
            card.isFocusable = true
            card.setOnClickListener {
                Toast.makeText(
                    this,
                    "Order ${order.orderId} • ${order.paymentMethod}",
                    Toast.LENGTH_SHORT
                ).show()
            }

            ordersContainer.addView(card, cardParams)
        }
    }


    private fun formatPrice(
        price: Int
    ): String {

        return NumberFormat
            .getCurrencyInstance(
                Locale("id", "ID")
            )
            .format(price)
            .replace(",00", "")
    }
}