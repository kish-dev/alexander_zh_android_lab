package alex.android.lab.presentation.view

import alex.android.lab.R
import alex.android.lab.databinding.ProductListItemBinding
import alex.android.lab.presentation.viewObject.ProductInListVO
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class ProductsAdapter(
    private val onItemClick: (String) -> Unit,
    private val onFavoriteClick: (String, Boolean) -> Unit
) : ListAdapter<ProductInListVO, ProductsAdapter.ProductViewHolder>(ProductDiffCallback()) {

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
        fun bind(product: ProductInListVO) {
            with(binding) {
                nameTV.text = product.name
                priceTV.text = product.price
                ratingView.progress = product.rating.toInt()
                Glide.with(root.context)
                    .load(product.image)
                    .into(productIV)
                tvViewCount.text = product.viewCount.toString()

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

    class ProductDiffCallback : DiffUtil.ItemCallback<ProductInListVO>() {
        override fun areItemsTheSame(oldItem: ProductInListVO, newItem: ProductInListVO) =
            oldItem.guid == newItem.guid

        override fun areContentsTheSame(oldItem: ProductInListVO, newItem: ProductInListVO) =
            oldItem == newItem
    }
}