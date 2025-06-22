package alex.android.lab.presentation.view

import alex.android.lab.R
import alex.android.lab.app.App
import alex.android.lab.databinding.FragmentCartBinding
import alex.android.lab.di.featureComponents.CartComponent
import alex.android.lab.di.featureComponents.CartModule
import alex.android.lab.di.featureComponents.DaggerCartComponent
import alex.android.lab.di.featureComponents.DaggerPdpComponent
import alex.android.lab.di.featureComponents.PdpComponent
import alex.android.lab.di.featureComponents.PdpModule
import alex.android.lab.domain.UiStates.UIStates
import alex.android.lab.presentation.customView.CartButtonView
import alex.android.lab.presentation.viewModel.CartViewModel
import alex.android.lab.presentation.viewModel.PdpViewModel
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel

class CartFragment(): Fragment() {

    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!

    private lateinit var cartButtonView: CartButtonView
    private lateinit var homeButton: View

    private lateinit var cartComponent: CartComponent
    private lateinit var vm: CartViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)

        cartComponent = DaggerCartComponent.builder()
            .appComponent((requireActivity().application as App).getAppComponent())
            .build()

        cartComponent.inject(this)
        vm = cartComponent.getCartViewModel()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCartBinding.inflate(inflater, container, false)
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

       binding.cartRecyclerView.adapter = adapter

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
                    adapter.submitList(products.data.filter { it.inCartCount > 0 })
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

        binding.reloadButton.setOnClickListener {
            binding.errorFrameLayout.visibility = View.GONE
            binding.loadingFrameLayout.visibility = View.VISIBLE
            vm.loadProductsDB()
        }

        vm.errorLD.observe(viewLifecycleOwner) { error ->
            binding.ErrorTypeTextView.text = error
        }
        vm.loadProductsDB()
    }

    private fun initBottomMenu() {
        val bottomMenuView = binding.cartButtonLayout.getChildAt(0)

        cartButtonView = bottomMenuView.findViewById(R.id.cartButtonView)
        homeButton = bottomMenuView.findViewById(R.id.homeButton)

        homeButton.setOnClickListener {
            findNavController().popBackStack(R.id.productsFragment, false)
        }
    }

    override fun onResume() {
        super.onResume()
        vm.loadProductsDB()
        vm.getInCartProductsCount()
    }

    private fun openPdpFragment(productId: String) {
        val action = CartFragmentDirections.actionCartFragmentToPDPFragment(
            id = productId
        )
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}