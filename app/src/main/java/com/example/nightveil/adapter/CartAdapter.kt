package com.example.nightveil.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.nightveil.R
import com.bumptech.glide.Glide
import com.example.nightveil.data.CartItem
import java.text.NumberFormat
import java.util.Locale

class CartAdapter(
    private val items: MutableList<CartItem>,
    private val onQuantityChanged: () -> Unit
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    class CartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val image: ImageView =
            itemView.findViewById(R.id.ivCartProduct)

        val name: TextView =
            itemView.findViewById(R.id.tvCartProductName)

        val price: TextView =
            itemView.findViewById(R.id.tvCartProductPrice)

        val quantity: TextView =
            itemView.findViewById(R.id.tvCartQuantity)

        val minus: TextView =
            itemView.findViewById(R.id.tvMinus)

        val plus: TextView =
            itemView.findViewById(R.id.tvPlus)

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CartViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(
                R.layout.item_cart,
                parent,
                false
            )

        return CartViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: CartViewHolder,
        position: Int
    ) {

        val item = items[position]

        Glide.with(holder.itemView.context)
            .load(item.product.imageUrl)
            .placeholder(R.drawable.ic_launcher_background)
            .error(R.drawable.ic_launcher_background)
            .centerCrop()
            .into(holder.image)

        holder.name.text = item.product.name

        val formattedPrice = NumberFormat
            .getCurrencyInstance(Locale("id", "ID"))
            .format(item.product.price)
            .replace(",00", "")

        holder.price.text = formattedPrice

        holder.quantity.text = item.quantity.toString()


        holder.plus.setOnClickListener {

            item.quantity++

            holder.quantity.text =
                item.quantity.toString()

            onQuantityChanged()
        }


        holder.minus.setOnClickListener {

            if (item.quantity > 1) {

                item.quantity--

                holder.quantity.text =
                    item.quantity.toString()

                onQuantityChanged()

            } else {

                items.removeAt(holder.bindingAdapterPosition)

                notifyItemRemoved(holder.bindingAdapterPosition)

                onQuantityChanged()
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}