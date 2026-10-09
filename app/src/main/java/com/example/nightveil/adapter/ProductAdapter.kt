package com.example.nightveil.adapter

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.nightveil.ProductDetailActivity
import com.example.nightveil.R
import com.example.nightveil.data.Product
import com.example.nightveil.data.WishlistRepository
import java.text.NumberFormat
import java.util.Locale

class ProductAdapter(
    private val products: List<Product>
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    class ProductViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {

        val image: ImageView =
            itemView.findViewById(R.id.ivProduct)

        val name: TextView =
            itemView.findViewById(R.id.tvProductName)

        val price: TextView =
            itemView.findViewById(R.id.tvProductPrice)

        val rating: TextView =
            itemView.findViewById(R.id.tvProductRating)

        val favorite: TextView =
            itemView.findViewById(R.id.tvFavorite)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(
                R.layout.item_product,
                parent,
                false
            )

        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ProductViewHolder,
        position: Int
    ) {

        val product = products[position]

        // =====================================================
        // DEBUG PRODUCT IMAGE URL
        // =====================================================

        Log.d(
            "PRODUCT_IMAGE",
            "${product.name} -> ${product.imageUrl}"
        )

        // =====================================================
        // PRODUCT NAME
        // =====================================================

        holder.name.text = product.name

        // =====================================================
        // PRICE
        // =====================================================

        val formattedPrice = NumberFormat
            .getCurrencyInstance(
                Locale("id", "ID")
            )
            .format(product.price)
            .replace(",00", "")

        holder.price.text = formattedPrice

        // =====================================================
        // RATING + SOLD
        // =====================================================

        holder.rating.text =
            "★ ${product.rating} • ${product.sold} sold"

        // =====================================================
        // PRODUCT IMAGE
        // =====================================================

        Glide.with(holder.itemView.context)
            .load(product.imageUrl)
            .placeholder(R.drawable.ic_launcher_background)
            .error(R.drawable.ic_launcher_background)
            .centerCrop()
            .listener(object : RequestListener<android.graphics.drawable.Drawable> {

                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<android.graphics.drawable.Drawable>,
                    isFirstResource: Boolean
                ): Boolean {

                    Log.e(
                        "PRODUCT_IMAGE",
                        "GAGAL LOAD IMAGE: ${product.name}"
                    )

                    Log.e(
                        "PRODUCT_IMAGE",
                        "URL: ${product.imageUrl}"
                    )

                    Log.e(
                        "PRODUCT_IMAGE",
                        "ERROR: ${e?.message}",
                        e
                    )

                    return false
                }

                override fun onResourceReady(
                    resource: android.graphics.drawable.Drawable,
                    model: Any,
                    target: Target<android.graphics.drawable.Drawable>?,
                    dataSource: com.bumptech.glide.load.DataSource,
                    isFirstResource: Boolean
                ): Boolean {

                    Log.d(
                        "PRODUCT_IMAGE",
                        "BERHASIL LOAD: ${product.name}"
                    )

                    return false
                }
            })
            .into(holder.image)

        // =====================================================
        // FAVORITE
        // =====================================================

        updateFavoriteAppearance(
            holder,
            product
        )

        holder.favorite.setOnClickListener {

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

            updateFavoriteAppearance(
                holder,
                product
            )
        }

        // =====================================================
        // OPEN PRODUCT DETAIL
        // =====================================================

        holder.itemView.setOnClickListener {

            val intent = Intent(
                holder.itemView.context,
                ProductDetailActivity::class.java
            )

            intent.putExtra(
                "PRODUCT_ID",
                product.id
            )

            holder.itemView.context.startActivity(intent)
        }
    }

    // =========================================================
    // FAVORITE APPEARANCE
    // =========================================================

    private fun updateFavoriteAppearance(
        holder: ProductViewHolder,
        product: Product
    ) {

        if (
            WishlistRepository.isFavorite(
                product.id
            )
        ) {

            holder.favorite.text = "♥"

            holder.favorite.setTextColor(
                holder.itemView.context.getColor(
                    R.color.burgundy
                )
            )

        } else {

            holder.favorite.text = "♡"

            holder.favorite.setTextColor(
                holder.itemView.context.getColor(
                    R.color.text_primary
                )
            )
        }
    }

    // =========================================================
    // ITEM COUNT
    // =========================================================

    override fun getItemCount(): Int {
        return products.size
    }
}