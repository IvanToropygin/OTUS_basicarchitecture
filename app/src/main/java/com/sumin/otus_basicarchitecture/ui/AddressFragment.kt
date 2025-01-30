package com.sumin.otus_basicarchitecture.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.sumin.otus_basicarchitecture.R
import com.sumin.otus_basicarchitecture.databinding.FragmentAddressBinding
import com.sumin.otus_basicarchitecture.viewmodel.AddressViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AddressFragment : Fragment() {

    private var _binding: FragmentAddressBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AddressViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentAddressBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.addressSuggestions.observe(viewLifecycleOwner) { message ->
            binding.textViewResult.text = message
        }

        binding.checkboxIsMyAddress.setOnClickListener {
            viewModel.onCheckBoxClicked(binding.checkboxIsMyAddress.isChecked)
            binding.btnNext.isEnabled = viewModel.isNextButtonEnabled()
        }

        binding.editTextAddress.addTextChangedListener(object : TextWatcher {
            private var job: Job? = null

            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                s?.let { query ->
                    if (query.isNotEmpty()) {
                        job?.cancel()
                        job = viewLifecycleOwner.lifecycleScope.launch {
                            delay(2_000L)
                            viewModel.fetchAddressSuggestions(query.toString().trim())
                        }
                    }
                }
            }
        })

        binding.btnNext.isEnabled = viewModel.isNextButtonEnabled()

        binding.btnNext.setOnClickListener {
            viewModel.onNextButtonClicked()
            findNavController().navigate(R.id.action_addressFragment_to_interestsFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}