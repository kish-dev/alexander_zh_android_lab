package alex.android.lab.presentation.view

import alex.android.lab.databinding.PdpFragmentBinding
import alex.android.lab.presentation.viewModel.PdpViewModel
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

        arguments?.getString("productId")?.let { productId ->
            vm.loadProduct(productId)
        }

        vm.productLD.observe(viewLifecycleOwner) { product ->
            with(binding) {
                nameTV.text = product.name
                priceTV.text = product.price
                ratingView.progress = product.rating.toInt()
                Glide.with(requireContext())
                    .load(product.image)
                    .into(productIV)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}