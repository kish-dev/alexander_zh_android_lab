package alex.android.lab.presentation.view

import alex.android.lab.databinding.ProductListItemBinding
import alex.android.lab.presentation.viewObject.ProductInListVO
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class ProductsAdapter(
    private val onItemClick: (String) -> Unit
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

                root.setOnClickListener {
                    onItemClick(product.guid)
                }
            }
        }
    }

    class ProductDiffCallback : DiffUtil.ItemCallback<ProductInListVO>() {
        override fun areItemsTheSame(oldItem: ProductInListVO, newItem: ProductInListVO) =
            oldItem.guid == newItem.guid

        override fun areContentsTheSame(oldItem: ProductInListVO, newItem: ProductInListVO) =
            oldItem == newItem
    }
}