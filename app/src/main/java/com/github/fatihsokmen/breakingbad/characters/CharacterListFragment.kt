package com.github.fatihsokmen.breakingbad.characters

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.github.fatihsokmen.breakingbad.R
import com.github.fatihsokmen.breakingbad.core.livedata.invoke
import com.github.fatihsokmen.breakingbad.data.CharacterModel
import com.github.fatihsokmen.breakingbad.databinding.FragmentCharactersBinding
import com.github.fatihsokmen.breakingbad.home.HomeViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class CharacterListFragment : Fragment(),
    CharacterSelectionHandler,
    SearchView.OnQueryTextListener,
    View.OnFocusChangeListener {

    private val viewModel: HomeViewModel by sharedViewModel()

    private lateinit var binding: FragmentCharactersBinding
    private lateinit var adapter: CharacterAdapter
    private lateinit var searchView: SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCharactersBinding.inflate(inflater, container, false)
        adapter = CharacterAdapter(this)
        binding.characters.adapter = adapter
        binding.characters.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.characterModels.observe(viewLifecycleOwner) {
            adapter.setData(it)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_characters, menu)
        searchView = (menu.findItem(R.id.menuShowSearch).actionView as SearchView)
        searchView.setOnQueryTextListener(this)
        searchView.setOnQueryTextFocusChangeListener(this)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menuShowFilter -> {
                onShowAppearanceFilterDialog()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCharacterClicked(character: CharacterModel) {
        viewModel.navigator.onNavigateCharacterDetails(character)
    }

    private fun onShowAppearanceFilterDialog() {
        viewModel.navigator.onNavigateAppearanceFilter(Unit)
    }

    override fun onQueryTextChange(text: String?): Boolean {
        viewModel.setSearchFilter(text.orEmpty())
        return true
    }

    override fun onQueryTextSubmit(text: String?): Boolean {
        searchView.clearFocus()
        return true
    }

    override fun onFocusChange(view: View?, focused: Boolean) {
        if (focused) {
            viewModel.getSearchFilter()?.let {
                searchView.setQuery(it, false)
            }
        }
    }
}