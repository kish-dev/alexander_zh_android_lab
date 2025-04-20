package alex.android.lab.presentation.view

import alex.android.lab.R
import alex.android.lab.databinding.FragmentProductsBinding
import alex.android.lab.presentation.viewModel.ProductsViewModel
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProductsFragment : Fragment() {
    private var _binding: FragmentProductsBinding? = null
    private val binding get() = _binding!!

    private val vm: ProductsViewModel by viewModel<ProductsViewModel>()
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

        val adapter = ProductsAdapter { productId ->
            openPdpFragment(productId)
        }

        binding.productsRecyclerView.adapter = adapter

        vm.productsLD.observe(viewLifecycleOwner) { products ->
            adapter.submitList(products)
        }
    }

    private fun openPdpFragment(productId: String) {
        val fragment = PDPFragment().apply {
            arguments = Bundle().apply {
                putString("productId", productId)
            }
        }

        parentFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}