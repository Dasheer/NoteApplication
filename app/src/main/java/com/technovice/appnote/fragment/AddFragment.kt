package com.technovice.appnote.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.navigation.findNavController
import com.technovice.appnote.MainActivity
import com.technovice.appnote.R
import com.technovice.appnote.databinding.FragmentAddBinding
import com.technovice.appnote.model.Note
import com.technovice.appnote.viewmodel.NoteViewModel

class AddFragment : Fragment(R.layout.fragment_add), MenuProvider {

  private var addNoteBinding : FragmentAddBinding? = null
  private val binding get() = addNoteBinding!!

  private lateinit var notesViewModel : NoteViewModel
  private lateinit var addNoteView: View

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    // Inflate the layout for this fragment
    addNoteBinding = FragmentAddBinding.inflate(inflater, container, false)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    val menuHost: MenuHost = requireActivity()
    menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

    notesViewModel = (activity as MainActivity).noteViewModel
    addNoteView = view
  }

  private fun save(view: View) {
    val noteTitle = binding.addNoteTitle.text.toString()
    val noteDescription = binding.addNoteDescription.text.toString()

    if (noteTitle.isNotEmpty()) {
      val note = Note(0, noteTitle, noteDescription)
      notesViewModel.addNote(note)

      Toast.makeText(addNoteView.context, "Note added successfully", Toast.LENGTH_SHORT).show()
      view.findNavController().popBackStack(R.id.homeFragment, false)

    } else {
      Toast.makeText(addNoteView.context, "Please enter a title", Toast.LENGTH_SHORT).show()
    }
  }

  override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
    menu.clear()
    menuInflater.inflate(R.menu.add_note, menu)
  }

  override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
    return when (menuItem.itemId) {
      R.id.save_menu_note -> {
        save(addNoteView)
        true
      }
      else -> {
        false
      }
    }
  }

  override fun onDestroy() {
    super.onDestroy()
    addNoteBinding = null
  }

}