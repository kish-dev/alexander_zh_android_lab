package alex.android.lab.presentation.view

import alex.android.lab.databinding.FragmentProductsBinding
import alex.android.lab.domain.UiStates.ApiResult
import alex.android.lab.presentation.viewModel.ProductsViewModel
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProductsFragment(
) : Fragment() {
    private var _binding: FragmentProductsBinding? = null
    private val binding get() = _binding!!

    private val vm: ProductsViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val products = arguments?.let { bundle ->
            val args =  ProductsFragmentArgs.fromBundle(bundle)
            args.products?.toList()?: emptyList()
        }?: emptyList()
        vm.setInitialData(products)


        val adapter = ProductsAdapter (
            onItemClick = { productId ->
                openPdpFragment(productId)
            },
            onFavoriteClick = { productId, isFavorite ->
                vm.toggleFavorite(productId, isFavorite)
            }
        )


        binding.productsRecyclerView.adapter = adapter

        vm.productsLD.observe(viewLifecycleOwner) { products ->
            when (products) {
                is ApiResult.Success -> {
                    adapter.submitList(products.data)
                }
                is ApiResult.Error -> {
                    val action = ProductsFragmentDirections.actionProductsFragmentToErrorPageFragment2(
                        error = products.error
                    )
                    findNavController().navigate(action)
                }
            }
        }

        vm.loadProducts()
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