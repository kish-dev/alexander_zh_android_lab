package alex.android.lab.presentation.view

import alex.android.lab.databinding.FragmentBasketBinding
import alex.android.lab.domain.UiStates.UIStates
import alex.android.lab.presentation.viewModel.BasketViewModel
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel

class BasketFragment(): Fragment() {

    private var _binding: FragmentBasketBinding? = null
    private val binding get() = _binding!!

    private val vm: BasketViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBasketBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = ProductsAdapter (
            onItemClick = { productId ->
                openPdpFragment(productId)
            },
            onFavoriteClick = { productId, isFavorite ->
                vm.toggleFavorite(productId, isFavorite)
            }
        )

       binding.basketRecyclerView.adapter = adapter

        vm.productsLD.observe(viewLifecycleOwner) { products ->
            when (products) {
                is UIStates.Loading -> {
                    binding.errorFrameLayout.visibility = View.GONE
                    binding.loadingFrameLayout.visibility = View.VISIBLE
                    vm.loadProductsDB()
                }
                is UIStates.Success -> {
                    binding.errorFrameLayout.visibility = View.GONE
                    binding.loadingFrameLayout.visibility = View.GONE
                    adapter.submitList(products.data.filter { it.isFavorite })
                }
                is UIStates.Error -> {
                    binding.loadingFrameLayout.visibility = View.GONE
                    binding.errorFrameLayout.visibility = View.VISIBLE
                    vm.setError(products.error)
                }
            }
        }

        binding.reloadButton.setOnClickListener {
            binding.errorFrameLayout.visibility = View.GONE
            binding.loadingFrameLayout.visibility = View.VISIBLE
            vm.loadProductsDB()
        }

        vm.errorLD.observe(viewLifecycleOwner) { error ->
            binding.ErrorTypeTextView.text = error
        }
    }

    override fun onResume() {
        super.onResume()
        vm.loadProductsDB()
    }

    private fun openPdpFragment(productId: String) {
        val action = ProductsFragmentDirections.actionProductsFragmentToPDPFragment(
            id = productId
        )
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}