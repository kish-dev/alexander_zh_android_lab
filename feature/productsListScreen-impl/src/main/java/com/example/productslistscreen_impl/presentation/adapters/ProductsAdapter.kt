package com.example.productslistscreen_impl.presentation.adapters

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.commonui.R
import com.example.commonui.databinding.ProductListItemBinding
import com.example.productslistscreen_impl.presentation.models.ProductInListVo

class ProductsAdapter(
    private val onItemClick: (String) -> Unit,
    private val onFavoriteClick: (String, Boolean) -> Unit
) : ListAdapter<ProductInListVo, ProductsAdapter.ProductViewHolder>(ProductDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ProductListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ProductViewHolder(
        private val binding: ProductListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(product: ProductInListVo) {
            with(binding) {
                nameTV.text = product.name
                priceTV.text = product.price
                ratingView.progress = product.rating.toInt()
                 Glide.with(root.context)
                    .load(product.image)
                    .into(productIV)
                tvViewCount.text = product.viewCount.toString()
                if (product.inCartCount > 0){
                    tvCartCount.text = product.inCartCount.toString()
                    ivCart.setVisibility(View.VISIBLE)
                } else {
                    ivCart.setVisibility(View.INVISIBLE)
                    tvCartCount.setVisibility(View.INVISIBLE)
                }

                updateLikeButton(product.isFavorite)

                root.setOnClickListener {
                    onItemClick(product.guid)
                }

                btnLike.setOnClickListener {
                    val newFavoriteState = !product.isFavorite
                    updateLikeButton(newFavoriteState)
                    onFavoriteClick(product.guid, newFavoriteState)
                }
            }
        }

        private fun updateLikeButton(isFavorite: Boolean) {
            val color = if (isFavorite) {
                ContextCompat.getColor(binding.root.context, R.color.purple_200)
            } else {
                ContextCompat.getColor(binding.root.context, R.color.black_overlay)
            }
            binding.btnLike.compoundDrawableTintList = ColorStateList.valueOf(color)
        }
    }

    class ProductDiffCallback : DiffUtil.ItemCallback<ProductInListVo>() {
        override fun areItemsTheSame(oldItem: ProductInListVo, newItem: ProductInListVo) =
            oldItem.guid == newItem.guid

        override fun areContentsTheSame(oldItem: ProductInListVo, newItem: ProductInListVo) =
            oldItem == newItem
    }
}