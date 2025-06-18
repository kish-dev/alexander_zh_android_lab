package alex.android.lab.presentation.view

import alex.android.lab.R
import alex.android.lab.app.App
import alex.android.lab.databinding.FragmentProductsBinding
import alex.android.lab.di.featureComponents.DaggerProductsComponent
import alex.android.lab.di.featureComponents.ProductsComponent
import alex.android.lab.di.featureComponents.ProductsModule
import alex.android.lab.domain.UiStates.UIStates
import alex.android.lab.presentation.customView.CartButtonView
import alex.android.lab.presentation.viewModel.ProductsViewModel
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import javax.inject.Inject

class ProductsFragment(
) : Fragment() {
    private var _binding: FragmentProductsBinding? = null
    private val binding get() = _binding!!

    private lateinit var cartButtonView: CartButtonView
    private lateinit var homeButton: View

    private lateinit var productsComponent: ProductsComponent
    private lateinit var vm: ProductsViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)

        productsComponent = DaggerProductsComponent.builder()
            .appComponent((requireActivity().application as App).getAppComponent())
            .productsModule(ProductsModule())
            .build()

        productsComponent.inject(this)
        vm = productsComponent.getProductsViewModel()
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
            findNavController().popBackStack(R.id.productsFragment, false)
        }
    }
    private fun openCartFragment() {
        val action = ProductsFragmentDirections.actionProductsFragmentToCartFragment()
        findNavController().navigate(action)
    }

    private fun openPdpFragment(productId: String) {
        val action = ProductsFragmentDirections.actionProductsFragmentToPDPFragment(
            id = productId
        )
        findNavController().navigate(action)
    }

    override fun onResume() {
        super.onResume()
        vm.getInCartProductsCount()
        vm.loadProductsDB()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}