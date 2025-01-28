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
import com.sumin.otus_basicarchitecture.databinding.FragmentPersonalInfoBinding
import com.sumin.otus_basicarchitecture.viewmodel.PersonalInfoViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PersonalInfoFragment : Fragment() {

    private var _binding: FragmentPersonalInfoBinding? = null
    private val binding get() = _binding!!

    private val viewModel: PersonalInfoViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentPersonalInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnNext.setOnClickListener {
            val name = binding.etFirstName.text.toString()
            val surname = binding.etLastName.text.toString()
            val birthDate = binding.etYearsCount.text.toString()

            if (viewModel.validateInput(name, surname, birthDate)) {
                viewModel.saveData(name, surname, birthDate)
                findNavController().navigate(R.id.action_personalInfoFragment_to_addressFragment)
            } else {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.fill_all_fields), Toast.LENGTH_SHORT
                ).show()
            }
        }

        viewModel.isNextButtonEnabled.observe(viewLifecycleOwner) { isEnabled ->
            binding.btnNext.isEnabled = isEnabled
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}