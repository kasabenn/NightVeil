package com.example.nightveil

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.nightveil.data.CartRepository
import com.example.nightveil.data.Order
import com.example.nightveil.data.OrderRepository
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class CheckoutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_checkout)

        if (CartRepository.getItems().isEmpty()) {
            Toast.makeText(this, "Your cart is empty", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        val tvBack =
            findViewById<TextView>(R.id.tvCheckoutBack)

        val etAddress =
            findViewById<EditText>(R.id.etAddress)

        val itemsContainer =
            findViewById<LinearLayout>(
                R.id.checkoutItemsContainer
            )

        val paymentGroup =
            findViewById<RadioGroup>(
                R.id.paymentGroup
            )

        val tvTotal =
            findViewById<TextView>(
                R.id.tvCheckoutTotal
            )

        val btnPlaceOrder =
            findViewById<Button>(
                R.id.btnPlaceOrder
            )


        // BACK
        tvBack.setOnClickListener {
            finish()
        }


        // LOAD CART ITEMS
        val cartItems =
            CartRepository.getItems()

        for (item in cartItems) {

            val itemLayout =
                LinearLayout(this)

            itemLayout.orientation =
                LinearLayout.HORIZONTAL

            itemLayout.setPadding(
                0,
                8,
                0,
                8
            )


            val productName =
                TextView(this)

            productName.text =
                "${item.product.name}  ×${item.quantity}"

            productName.setTextColor(
                getColor(R.color.text_primary)
            )

            productName.textSize = 13f


            val itemPrice =
                TextView(this)

            val subtotal =
                item.product.price * item.quantity

            itemPrice.text =
                formatPrice(subtotal)

            itemPrice.setTextColor(
                getColor(R.color.burgundy)
            )

            itemPrice.textSize = 13f

            itemPrice.gravity =
                Gravity.END


            itemLayout.addView(
                productName,
                LinearLayout.LayoutParams(
                    0,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    1f
                )
            )

            itemLayout.addView(
                itemPrice,
                LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
            )

            itemsContainer.addView(
                itemLayout
            )
        }


        // TOTAL
        tvTotal.text =
            formatPrice(
                CartRepository.getTotalPrice()
            )


        // PLACE ORDER
        btnPlaceOrder.setOnClickListener {

            val address =
                etAddress.text.toString().trim()

            val selectedPayment =
                paymentGroup.checkedRadioButtonId


            // CHECK ADDRESS
            if (address.isEmpty()) {

                etAddress.error =
                    "Address is required"

                etAddress.requestFocus()

                return@setOnClickListener
            }


            // CHECK PAYMENT
            if (selectedPayment == -1) {

                Toast.makeText(
                    this,
                    "Please select a payment method",
                    Toast.LENGTH_SHORT
                ).show()

                return@setOnClickListener
            }


            // PAYMENT NAME
            val selectedRadioButton =
                findViewById<RadioButton>(
                    selectedPayment
                )

            val paymentMethod =
                selectedRadioButton.text.toString()


            // ORDER ID
            val orderId =
                "NV" + System.currentTimeMillis()
                    .toString()
                    .takeLast(8)


            // DATE
            val date =
                SimpleDateFormat(
                    "dd MMM yyyy, HH:mm",
                    Locale("id", "ID")
                ).format(Date())


            // TOTAL
            val total =
                CartRepository.getTotalPrice()


            // SAVE ORDER
            val order =
                Order(
                    orderId = orderId,
                    date = date,
                    total = total,
                    paymentMethod = paymentMethod,
                    address = address
                )

            OrderRepository.addOrder(order)


            // CLEAR CART
            CartRepository.clearCart()


            // OPEN SUCCESS
            val intent =
                Intent(
                    this,
                    OrderSuccessActivity::class.java
                )

            intent.putExtra(
                "ORDER_ID",
                orderId
            )

            startActivity(intent)

            finish()
        }
    }


    private fun formatPrice(
        price: Int
    ): String {

        return java.text.NumberFormat
            .getCurrencyInstance(
                Locale("id", "ID")
            )
            .format(price)
            .replace(",00", "")
    }
}