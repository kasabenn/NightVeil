package com.example.nightveil

import android.os.Bundle
import android.view.Gravity
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.nightveil.data.WishlistRepository
import java.text.NumberFormat
import java.util.Locale

class WishlistActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_wishlist)

        val tvBack =
            findViewById<TextView>(
                R.id.tvWishlistBack
            )

        val container =
            findViewById<LinearLayout>(
                R.id.wishlistContainer
            )

        tvBack.setOnClickListener {
            finish()
        }

        loadWishlist(container)
    }

    private fun loadWishlist(
        container: LinearLayout
    ) {

        val products =
            WishlistRepository.getWishlist()

        if (products.isEmpty()) {

            val emptyText =
                TextView(this)

            emptyText.text =
                "Your wishlist is empty.\n\nStart adding your favorite pieces."

            emptyText.gravity =
                Gravity.CENTER

            emptyText.setTextColor(
                getColor(R.color.text_secondary)
            )

            emptyText.textSize = 14f

            emptyText.setPadding(
                20,
                120,
                20,
                120
            )

            container.addView(
                emptyText
            )

            return
        }

        for (product in products) {

            val card =
                LinearLayout(this)

            card.orientation =
                LinearLayout.HORIZONTAL

            card.setPadding(
                16,
                16,
                16,
                16
            )

            card.background =
                getDrawable(
                    R.drawable.bg_search
                )

            val params =
                LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )

            params.setMargins(
                0,
                0,
                0,
                12
            )

            // PRODUCT IMAGE
            val image = ImageView(this)
            image.layoutParams = LinearLayout.LayoutParams(60, 60)
            image.scaleType = ImageView.ScaleType.CENTER_CROP
            image.contentDescription = product.name

            Glide.with(this)
                .load(product.imageUrl)
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
                .centerCrop()
                .into(image)

            // INFO
            val info =
                LinearLayout(this)

            info.orientation =
                LinearLayout.VERTICAL

            info.layoutParams =
                LinearLayout.LayoutParams(
                    0,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    1f
                )

            info.setPadding(
                16,
                0,
                10,
                0
            )

            // PRODUCT NAME
            val name =
                TextView(this)

            name.text =
                product.name

            name.setTextColor(
                getColor(R.color.text_primary)
            )

            name.textSize =
                14f

            name.setTypeface(
                null,
                android.graphics.Typeface.BOLD
            )

            // CATEGORY
            val category =
                TextView(this)

            category.text =
                product.category

            category.setTextColor(
                getColor(R.color.text_secondary)
            )

            category.textSize =
                11f

            category.setPadding(
                0,
                4,
                0,
                4
            )

            // PRICE
            val price =
                TextView(this)

            price.text =
                formatPrice(product.price)

            price.setTextColor(
                getColor(R.color.burgundy)
            )

            price.textSize =
                13f

            price.setTypeface(
                null,
                android.graphics.Typeface.BOLD
            )

            info.addView(name)
            info.addView(category)
            info.addView(price)

            // REMOVE FAVORITE
            val remove =
                TextView(this)

            remove.layoutParams =
                LinearLayout.LayoutParams(
                    45,
                    45
                )

            remove.gravity =
                Gravity.CENTER

            remove.text =
                "♥"

            remove.setTextColor(
                getColor(R.color.burgundy)
            )

            remove.textSize =
                23f

            card.addView(image)
            card.addView(info)
            card.addView(remove)

            card.setOnClickListener {
                startActivity(
                    android.content.Intent(
                        this,
                        ProductDetailActivity::class.java
                    ).putExtra("PRODUCT_ID", product.id)
                )
            }

            remove.setOnClickListener {
                WishlistRepository.removeProduct(product.id)
                container.removeAllViews()
                loadWishlist(container)
            }

            container.addView(card, params)
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