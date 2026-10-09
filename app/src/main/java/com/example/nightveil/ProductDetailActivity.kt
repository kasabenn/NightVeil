package com.example.nightveil

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.nightveil.data.CartRepository
import com.example.nightveil.data.Product
import com.example.nightveil.data.ProductRepository
import com.example.nightveil.data.WishlistRepository
import java.text.NumberFormat
import java.util.Locale

class ProductDetailActivity : AppCompatActivity() {

    private lateinit var product: Product
    private var quantity = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_product_detail)

        val productId =
            intent.getIntExtra("PRODUCT_ID", -1)

        product =
            ProductRepository.products.find {
                it.id == productId
            } ?: run {
                finish()
                return
            }

        val tvBack =
            findViewById<TextView>(
                R.id.tvDetailBack
            )

        val tvFavorite =
            findViewById<TextView>(
                R.id.tvDetailFavorite
            )

        val ivProduct =
            findViewById<ImageView>(R.id.ivDetailProduct)

        val tvName =
            findViewById<TextView>(
                R.id.tvDetailName
            )

        val tvCategory =
            findViewById<TextView>(
                R.id.tvDetailCategory
            )

        val tvRating =
            findViewById<TextView>(
                R.id.tvDetailRating
            )

        val tvPrice =
            findViewById<TextView>(
                R.id.tvDetailPrice
            )

        val tvDescription =
            findViewById<TextView>(
                R.id.tvDetailDescription
            )

        val tvQuantity =
            findViewById<TextView>(
                R.id.tvQuantity
            )

        val btnMinus =
            findViewById<TextView>(
                R.id.btnMinus
            )

        val btnPlus =
            findViewById<TextView>(
                R.id.btnPlus
            )

        val btnAddCart =
            findViewById<Button>(
                R.id.btnAddToCart
            )

        val btnBuyNow =
            findViewById<Button>(
                R.id.btnBuyNow
            )

        tvBack.setOnClickListener {
            finish()
        }

        Glide.with(this)
            .load(product.imageUrl)
            .placeholder(R.drawable.ic_launcher_background)
            .error(R.drawable.ic_launcher_background)
            .centerCrop()
            .into(ivProduct)

        tvName.text =
            product.name

        tvCategory.text =
            product.category.uppercase()

        tvRating.text =
            "★ ${product.rating}  •  ${product.sold} sold"

        tvPrice.text =
            formatPrice(product.price)

        tvDescription.text =
            product.description

        updateFavoriteButton(tvFavorite)

        tvFavorite.setOnClickListener {

            if (
                WishlistRepository.isFavorite(
                    product.id
                )
            ) {

                WishlistRepository.removeProduct(
                    product.id
                )

            } else {

                WishlistRepository.addProduct(
                    product
                )
            }

            updateFavoriteButton(tvFavorite)
        }

        btnMinus.setOnClickListener {

            if (quantity > 1) {
                quantity--
                tvQuantity.text =
                    quantity.toString()
            }
        }

        btnPlus.setOnClickListener {

            quantity++

            tvQuantity.text =
                quantity.toString()
        }

        btnAddCart.setOnClickListener {

            repeat(quantity) {
                CartRepository.addProduct(
                    product
                )
            }

            Toast.makeText(
                this,
                "${product.name} added to cart",
                Toast.LENGTH_SHORT
            ).show()
        }

        btnBuyNow.setOnClickListener {

            repeat(quantity) {
                CartRepository.addProduct(
                    product
                )
            }

            val intent =
                Intent(
                    this,
                    CheckoutActivity::class.java
                )

            startActivity(intent)
        }
    }

    private fun updateFavoriteButton(
        button: TextView
    ) {

        if (
            WishlistRepository.isFavorite(
                product.id
            )
        ) {

            button.text = "♥"

            button.setTextColor(
                getColor(
                    R.color.burgundy
                )
            )

        } else {

            button.text = "♡"

            button.setTextColor(
                getColor(
                    R.color.text_primary
                )
            )
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