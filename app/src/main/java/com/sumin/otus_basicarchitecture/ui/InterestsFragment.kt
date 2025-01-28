package com.sumin.otus_basicarchitecture.ui

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.android.material.chip.Chip
import com.sumin.otus_basicarchitecture.R
import com.sumin.otus_basicarchitecture.databinding.FragmentInterestsBinding
import com.sumin.otus_basicarchitecture.viewmodel.InterestsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InterestsFragment : Fragment() {

    private var _binding: FragmentInterestsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: InterestsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInterestsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Заполняем ChipGroup интересами
        val interests = listOf("Спорт", "Музыка", "Кино", "Путешествия", "Книги")
        interests.forEach { interest ->
            val chip = Chip(requireContext()).apply {
                text = interest
                isCheckable = true
            }
            binding.chipGroup.addView(chip)
        }

        binding.btnNext.setOnClickListener {
            val selectedInterests = binding.chipGroup.checkedChipIds
                .map { id -> binding.chipGroup.findViewById<Chip>(id).text.toString() }

            if (selectedInterests.isNotEmpty()) {
                viewModel.saveData(selectedInterests)
                findNavController().navigate(R.id.action_interestsFragment_to_summaryFragment)
            } else {
                Toast.makeText(requireContext(),
                    getString(R.string.choose_interest), Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}