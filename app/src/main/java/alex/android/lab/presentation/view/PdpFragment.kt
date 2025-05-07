package alex.android.lab.presentation.view

import alex.android.lab.R
import alex.android.lab.databinding.PdpFragmentBinding
import alex.android.lab.presentation.viewModel.PdpViewModel
import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import org.koin.androidx.viewmodel.ext.android.viewModel

class PDPFragment : Fragment() {
    private var _binding: PdpFragmentBinding? = null
    private val binding get() = _binding!!

    private val vm: PdpViewModel by viewModel<PdpViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = PdpFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val productId = arguments?.let {
            PDPFragmentArgs.fromBundle(it).id
        }
        vm.loadProduct(productId.toString())

        vm.productLD.observe(viewLifecycleOwner) { product ->
            with(binding) {
                nameTV.text = product.name
                priceTV.text = product.price
                ratingView.progress = product.rating.toInt()
                Glide.with(requireContext())
                    .load(product.image)
                    .into(productIV)
                updateLikeButton(product.isFavorite)
                tvViewCount.text = product.viewCount.toString()
                btnLike.setOnClickListener {
                    val newFavoriteState = !product.isFavorite
                    updateLikeButton(newFavoriteState)
                    vm.toggleFavorite(product.guid, newFavoriteState)
                }
            }
        }
    }

    private fun updateLikeButton(isFavorite: Boolean) {
        val color = if (isFavorite) {
            ContextCompat.getColor(requireContext(), R.color.purple_200)
        } else {
            ContextCompat.getColor(requireContext(), R.color.black_overlay)
        }
        binding.btnLike.compoundDrawableTintList = ColorStateList.valueOf(color)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}