package alex.android.lab.presentation.view

import alex.android.lab.databinding.FragmentErrorPageBinding
import alex.android.lab.presentation.viewModel.ErrorPageViewModel
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel

class ErrorPageFragment : Fragment() {
    private var _binding: FragmentErrorPageBinding? = null
    private val binding get() = _binding!!

    private val vm: ErrorPageViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentErrorPageBinding.inflate(inflater, container, false)
        return binding.root
}
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val error = arguments?.let {
            ErrorPageFragmentArgs.fromBundle(it).error
        }
        vm.setError(error.toString())

        vm.errorLD.observe(viewLifecycleOwner) { error ->
            binding.ErrorTypeTextView.text = error
        }

        binding.reloadButton.setOnClickListener {
            val action = ErrorPageFragmentDirections.actionErrorPageFragmentToLoadingFragment2()
            findNavController().navigate(action)
        }


    }

}