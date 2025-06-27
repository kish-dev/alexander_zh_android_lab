package alex.android.lab.presentation.view

import alex.android.lab.R
import alex.android.lab.app.App
import alex.android.lab.databinding.PdpFragmentBinding
import alex.android.lab.di.featureComponents.DaggerPdpComponent
import alex.android.lab.presentation.customView.CartButtonView
import alex.android.lab.presentation.viewModel.PdpViewModel
import android.content.Context
import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import javax.inject.Inject

class PdpFragment : Fragment() {
    private var _binding: PdpFragmentBinding? = null
    private val binding get() = _binding!!

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    private val vm: PdpViewModel by viewModels { viewModelFactory }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        DaggerPdpComponent.builder()
            .appComponent((requireActivity().application as App).getAppComponent())
            .build()
            .inject(this)
    }

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
            PdpFragmentArgs.fromBundle(it).id
        }
        vm.loadProduct(productId.toString())

        vm.productLD.observe(viewLifecycleOwner) { product ->
            with(binding) {
                nameTV.text = product.name
                priceTV.text = product.price
                ratingView.progress = product.rating.toInt()
                tvViewCount.text = product.viewCount.toString()
                Glide.with(requireContext())
                    .load(product.image)
                    .into(productIV)
                updateLikeButton(product.isFavorite)

                btnLike.setOnClickListener {
                    val newFavoriteState = !product.isFavorite
                    updateLikeButton(newFavoriteState)
                    vm.toggleFavorite(product.guid, newFavoriteState)
                }
                cartButtonViewCounter.apply {
                    setMode(CartButtonView.Mode.EXPANDABLE)
                    if (product.inCartCount > 0) {
                        animateStateChange(CartButtonView.State.QUANTITY_CONTROL)
                        setInCartCount(product.inCartCount)
                    }
                    setOnCartChangeListener { count ->
                        vm.updateInCartCount(product.guid, count)
                    }
                    onFirstAddToCartListener = {
                        setInCartCount(1)
                        vm.updateInCartCount(product.guid, 1)
                    }
                    onAddToCartFromQuantityControlListener = {
                        val action = PdpFragmentDirections.actionPDPFragmentToCartFragment()
                        findNavController().navigate(action)
                    }

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


    override fun onResume() {
        super.onResume()
        vm.getInCartProductsCount()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}