package com.github.fatihsokmen.breakingbad.filter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.children
import com.github.fatihsokmen.breakingbad.databinding.FragmentAppearanceFilterBinding
import com.github.fatihsokmen.breakingbad.databinding.ViewFilterChipBinding
import com.github.fatihsokmen.breakingbad.home.HomeViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class AppearanceFilterFragment : BottomSheetDialogFragment() {

    private val viewModel: HomeViewModel by sharedViewModel()

    private lateinit var binding: FragmentAppearanceFilterBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAppearanceFilterBinding.inflate(
            inflater,
            container,
            false
        )
        binding.ok.setOnClickListener {
            onUpdateFilters()
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.seasonFilters.observe(viewLifecycleOwner) { filters ->
            bindFilters(filters)
        }
    }

    private fun bindFilters(filters: List<AppearanceFilterModel>) {
        filters.forEach {
            val viewBinding = ViewFilterChipBinding.inflate(layoutInflater)
            viewBinding.model = it
            binding.filters.addView(viewBinding.root)
        }
    }

    private fun onUpdateFilters() {
        val selectedSeasons = binding.filters.children.toList()
            .map { it as Chip }
            .filter { it.isChecked }
            .map { it.tag as Int }

        viewModel.setSeasonFilters(selectedSeasons)

        dismiss()
    }
}