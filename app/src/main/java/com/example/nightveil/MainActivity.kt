package com.example.nightveil

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nightveil.adapter.ProductAdapter
import com.example.nightveil.api.RetrofitClient
import com.example.nightveil.data.ProductRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var rvProducts: RecyclerView
    private lateinit var etSearch: EditText

    private var selectedCategory = "All"
    private var searchKeyword = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        // =====================================================
        // VIEW
        // =====================================================

        rvProducts = findViewById(R.id.rvProducts)
        etSearch = findViewById(R.id.etSearch)

        // =====================================================
        // SETUP
        // =====================================================

        setupProducts()
        setupSearch()
        setupCategories()
        setupNavigation()

        // =====================================================
        // LOAD PRODUCTS
        // =====================================================

        loadProductsFromBackend()
    }

    // =========================================================
    // PRODUCTS
    // =========================================================

    private fun setupProducts() {

        rvProducts.layoutManager = GridLayoutManager(
            this,
            2
        )

        rvProducts.setHasFixedSize(false)

        rvProducts.adapter = ProductAdapter(
            emptyList()
        )
    }

    // =========================================================
    // LOAD PRODUCTS FROM SUPABASE
    // =========================================================

    private fun loadProductsFromBackend() {

        lifecycleScope.launch {

            try {

                val products = withContext(Dispatchers.IO) {

                    RetrofitClient
                        .apiService
                        .getProducts()
                }

                // Simpan produk dari Supabase
                ProductRepository.setProducts(products)

                // Tampilkan produk
                updateProducts()

            } catch (e: Exception) {

                e.printStackTrace()

                showError(
                    "Gagal mengambil produk: ${e.message}"
                )
            }
        }
    }

    // =========================================================
    // UPDATE PRODUCTS
    // =========================================================

    private fun updateProducts() {

        val filteredProducts =
            ProductRepository.products.filter { product ->

                // CATEGORY
                val categoryMatch =
                    selectedCategory == "All" ||
                            product.category.equals(
                                selectedCategory,
                                ignoreCase = true
                            )

                // SEARCH
                val searchMatch =
                    searchKeyword.isEmpty() ||
                            product.name.contains(
                                searchKeyword,
                                ignoreCase = true
                            ) || product.category.contains(
                                searchKeyword,
                                ignoreCase = true
                            )

                // FINAL FILTER
                categoryMatch && searchMatch
            }

        rvProducts.adapter =
            ProductAdapter(filteredProducts)
    }

    // =========================================================
    // SEARCH
    // =========================================================

    private fun setupSearch() {

        etSearch.addTextChangedListener(

            object : TextWatcher {

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                    // Tidak digunakan
                }

                override fun onTextChanged(
                    s: CharSequence?,
                    start: Int,
                    before: Int,
                    count: Int
                ) {

                    searchKeyword =
                        s?.toString()?.trim() ?: ""

                    updateProducts()
                }

                override fun afterTextChanged(
                    s: Editable?
                ) {
                    // Tidak digunakan
                }
            }
        )
    }

    // =========================================================
    // CATEGORY
    // =========================================================

    private fun setupCategories() {

        val tvAll =
            findViewById<TextView>(
                R.id.tvCategoryAll
            )

        val tvAccessories =
            findViewById<TextView>(
                R.id.tvCategoryAccessories
            )

        val tvFashion =
            findViewById<TextView>(
                R.id.tvCategoryFashion
            )

        // =====================================================
        // ALL
        // =====================================================

        tvAll.setOnClickListener {

            selectedCategory = "All"

            updateCategoryAppearance(
                tvAll,
                tvAccessories,
                tvFashion
            )

            updateProducts()
        }

        // =====================================================
        // ACCESSORIES
        // =====================================================

        tvAccessories.setOnClickListener {

            selectedCategory = "Accessories"

            updateCategoryAppearance(
                tvAccessories,
                tvAll,
                tvFashion
            )

            updateProducts()
        }

        // =====================================================
        // FASHION
        // =====================================================

        tvFashion.setOnClickListener {

            selectedCategory = "Fashion"

            updateCategoryAppearance(
                tvFashion,
                tvAll,
                tvAccessories
            )

            updateProducts()
        }

        // =====================================================
        // DEFAULT
        // =====================================================

        updateCategoryAppearance(
            tvAll,
            tvAccessories,
            tvFashion
        )
    }

    // =========================================================
    // CATEGORY APPEARANCE
    // =========================================================

    private fun updateCategoryAppearance(
        selected: TextView,
        otherOne: TextView,
        otherTwo: TextView
    ) {

        // Selected category

        selected.setBackgroundResource(
            R.drawable.bg_category_selected
        )

        selected.setTextColor(
            getColor(R.color.white)
        )

        // Other category 1

        otherOne.setBackgroundResource(
            R.drawable.bg_category
        )

        otherOne.setTextColor(
            getColor(R.color.text_secondary)
        )

        // Other category 2

        otherTwo.setBackgroundResource(
            R.drawable.bg_category
        )

        otherTwo.setTextColor(
            getColor(R.color.text_secondary)
        )
    }

    // =========================================================
    // NAVIGATION
    // =========================================================

    private fun setupNavigation() {

        // =====================================================
        // HEADER WISHLIST
        // =====================================================

        val tvHomeWishlist =
            findViewById<TextView>(R.id.tvHomeWishlist)

        tvHomeWishlist.setOnClickListener {
            startActivity(Intent(this, WishlistActivity::class.java))
        }

        // =====================================================
        // HEADER CART
        // =====================================================

        val tvHomeCart =
            findViewById<TextView>(R.id.tvHomeCart)

        tvHomeCart.setOnClickListener {
            openCart()
        }

        // =====================================================
        // WISHLIST
        // =====================================================

        val tvBottomWishlist =
            findViewById<TextView>(
                R.id.tvBottomWishlist
            )

        tvBottomWishlist.setOnClickListener {

            val intent =
                Intent(
                    this,
                    WishlistActivity::class.java
                )

            startActivity(intent)
        }

        // =====================================================
        // CART
        // =====================================================

        val tvBottomCart =
            findViewById<TextView>(
                R.id.tvBottomCart
            )

        tvBottomCart.setOnClickListener {
            openCart()
        }

        // =====================================================
        // PROFILE
        // =====================================================

        val tvBottomProfile =
            findViewById<TextView>(
                R.id.tvBottomProfile
            )

        tvBottomProfile.setOnClickListener {

            val intent =
                Intent(
                    this,
                    ProfileActivity::class.java
                )

            startActivity(intent)
        }
    }

    // =========================================================
    // OPEN CART
    // =========================================================

    private fun openCart() {

        val intent =
            Intent(
                this,
                CartActivity::class.java
            )

        startActivity(intent)
    }

    // =========================================================
    // ERROR
    // =========================================================

    private fun showError(
        message: String
    ) {

        Toast.makeText(
            this,
            message,
            Toast.LENGTH_LONG
        ).show()
    }
}