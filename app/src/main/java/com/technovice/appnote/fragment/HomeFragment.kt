package com.technovice.appnote.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.navigation.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.technovice.appnote.MainActivity
import com.technovice.appnote.R
import com.technovice.appnote.adapter.NoteAdapter
import com.technovice.appnote.databinding.FragmentHomeBinding
import com.technovice.appnote.model.Note
import com.technovice.appnote.viewmodel.NoteViewModel

class HomeFragment : Fragment(R.layout.fragment_home), SearchView.OnQueryTextListener, MenuProvider {

  private var homeBinding: FragmentHomeBinding? = null
  private val binding get() = homeBinding!!

  private lateinit var notesViewModel : NoteViewModel
  private lateinit var noteAdapter: NoteAdapter

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    // Inflate the layout for this fragment
    homeBinding = FragmentHomeBinding.inflate(inflater, container, false)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    val menuHost: MenuHost = requireActivity()
    menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

    notesViewModel = (activity as MainActivity).noteViewModel

    binding.addNoteFab.setOnClickListener {
      it.findNavController().navigate(R.id.action_homeFragment_to_addFragment)
    }
  }

  private fun updateUI(note: List<Note>?) {
    if (note != null) {
      if (note.isNotEmpty()) {
        binding.emptyNotesImage.visibility = View.GONE
        binding.homeRecyclerView.visibility = View.VISIBLE
      } else {
        binding.emptyNotesImage.visibility = View.VISIBLE
        binding.homeRecyclerView.visibility = View.GONE
      }
    }
  }

  private fun setupHomeRecyclerView() {
    noteAdapter = NoteAdapter()
    binding.homeRecyclerView.apply {
      layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
      setHasFixedSize(true)
      adapter = noteAdapter
    }

    activity?.let {
      notesViewModel.getAllNotes().observe(viewLifecycleOwner) { note ->
        noteAdapter.differ.submitList(note)
        updateUI(note)
      }
    }
  }

  private fun searchNote(query: String?) {
    val searchQuery = "%$query"

    notesViewModel.searchNotes(searchQuery).observe(this) { list ->
      noteAdapter.differ.submitList(list)
    }
  }

  override fun onQueryTextSubmit(query: String?): Boolean {
    return false
  }

  override fun onQueryTextChange(newText: String?): Boolean {
    if (newText != null) {
      searchNote(newText)
    }
    return true
  }

  override fun onDestroy() {
    super.onDestroy()
    homeBinding = null
  }

  override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
    menu.clear()
    menuInflater.inflate(R.menu.menu, menu)

    val menuSearch = menu.findItem(R.id.search_menu).actionView as SearchView
    menuSearch.isSubmitButtonEnabled = false
    menuSearch.setOnQueryTextListener(this)
  }

  override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
    return false
  }

}