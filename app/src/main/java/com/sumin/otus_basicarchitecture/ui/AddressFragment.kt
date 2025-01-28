package com.sumin.otus_basicarchitecture.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.sumin.otus_basicarchitecture.R
import com.sumin.otus_basicarchitecture.databinding.FragmentAddressBinding
import com.sumin.otus_basicarchitecture.viewmodel.AddressViewModel
import dagger.hilt.android.AndroidEntryPoint

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

        binding.btnNext.setOnClickListener {
            val country = binding.etCountry.text.toString()
            val city = binding.etCity.text.toString()
            val address = binding.etAddress.text.toString()

            if (viewModel.validateInput(country, city, address)) {
                viewModel.saveData(country, city, address)
                findNavController().navigate(R.id.action_addressFragment_to_interestsFragment)
            } else {
                Toast.makeText(requireContext(), R.string.fill_all_fields, Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}