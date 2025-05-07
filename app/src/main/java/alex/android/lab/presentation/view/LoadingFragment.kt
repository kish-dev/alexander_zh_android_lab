package alex.android.lab.presentation.view

import alex.android.lab.databinding.FragmentLoadingBinding
import alex.android.lab.domain.UiStates.ApiResult
import alex.android.lab.presentation.viewModel.LoadingViewModel
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoadingFragment: Fragment() {
    private var _binding: FragmentLoadingBinding? = null
    private val binding get() = _binding!!

    private val vm: LoadingViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoadingBinding.inflate(inflater, container, false)
        return binding.root
    }
    override  fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        vm.productsLD.observe(viewLifecycleOwner) { products ->
            when (products) {
                is ApiResult.Success -> {
                    val action = LoadingFragmentDirections.actionLoadingFragmentToProductsFragment(
                        products = products.data.toTypedArray()
                    )
                    findNavController().navigate(action)
                }
                is ApiResult.Error -> {
                    val action = LoadingFragmentDirections.actionLoadingFragmentToErrorPageFragment(
                        error = products.error
                    )
                    findNavController().navigate(action)

                }
            }
        }
    }
}