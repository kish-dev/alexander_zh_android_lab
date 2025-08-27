package com.example.productslistscreen_impl.presentation

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.commonui.customView.CartButtonView
import com.example.navigation_api.NavigationApi
import com.example.productslistscreen_impl.R
import com.example.productslistscreen_impl.databinding.FragmentProductsBinding
import com.example.productslistscreen_impl.di.ProductsScreenComponentHolder
import com.example.productslistscreen_impl.domain.models.UIStates
import com.example.productslistscreen_impl.navigation.ProductsScreenDirections
import com.example.productslistscreen_impl.presentation.adapters.ProductsAdapter
import com.example.productslistscreen_impl.presentation.viewModel.ProductsViewModel
import javax.inject.Inject

class ProductsFragment(
) : Fragment() {
    private var _binding: FragmentProductsBinding? = null
    private val binding get() = _binding!!

    private lateinit var cartButtonView: CartButtonView
    private lateinit var homeButton: View

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val vm: ProductsViewModel by viewModels { viewModelFactory }

    @Inject
    lateinit var navigationApi: NavigationApi<ProductsScreenDirections>

    override fun onAttach(context: Context) {
        ProductsScreenComponentHolder.get()
            .inject(this)
        super.onAttach(context)
    }

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

        initBottomMenu()

        val adapter = ProductsAdapter(
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
                is UIStates.Loading -> {
                    binding.errorFrameLayout.visibility = View.GONE
                    binding.loadingFrameLayout.visibility = View.VISIBLE
                    vm.loadProducts()
                }
                is UIStates.Success -> {
                    binding.errorFrameLayout.visibility = View.GONE
                    binding.loadingFrameLayout.visibility = View.GONE
                    adapter.submitList(products.data)
                }
                is UIStates.Error -> {
                    binding.loadingFrameLayout.visibility = View.GONE
                    binding.errorFrameLayout.visibility = View.VISIBLE
                    vm.setError(products.error)
                }
            }
        }

        vm.inCartProductsCountLD.observe(viewLifecycleOwner) { count ->
            cartButtonView.setBadgeCount(count)
        }

        vm.errorLD.observe(viewLifecycleOwner) { error ->
            binding.ErrorTypeTextView.text = error
        }

        binding.reloadButton.setOnClickListener {
            binding.errorFrameLayout.visibility = View.GONE
            binding.loadingFrameLayout.visibility = View.VISIBLE
            vm.loadProducts()
        }
    }

    private fun initBottomMenu() {
        val bottomMenuView = binding.cartButtonLayout.getChildAt(0)

        cartButtonView = bottomMenuView.findViewById(R.id.cartButtonView)
        homeButton = bottomMenuView.findViewById(R.id.homeButton)

        cartButtonView.apply {
            setMode(CartButtonView.Mode.SIMPLE)
            setOnClickListener {
                openCartFragment()
            }
        }

        homeButton.setOnClickListener {
           // findNavController().popBackStack(R.id., false)
        }
    }
    private fun openCartFragment() {
        navigationApi.navigate(ProductsScreenDirections.ToCartScreen)
    }

    private fun openPdpFragment(productId: String) {
        navigationApi.navigate(ProductsScreenDirections.ToPdpScreen(productId))
    }

    override fun onResume() {
        super.onResume()
        vm.getInCartProductsCount()
        vm.loadProductsDB()
    }

    override fun onDetach() {
        if (isRemoving) {
            ProductsScreenComponentHolder.clear()
        }
        super.onDetach()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}