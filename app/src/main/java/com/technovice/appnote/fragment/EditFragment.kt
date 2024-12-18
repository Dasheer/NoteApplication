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
import androidx.appcompat.app.AlertDialog
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.technovice.appnote.MainActivity
import com.technovice.appnote.R
import com.technovice.appnote.databinding.FragmentEditBinding
import com.technovice.appnote.model.Note
import com.technovice.appnote.viewmodel.NoteViewModel


class EditFragment : Fragment(R.layout.fragment_edit), MenuProvider {

  private var editNoteBinding: FragmentEditBinding? = null
  private val binding get() = editNoteBinding!!

  private lateinit var notesViewModel: NoteViewModel
  private lateinit var currentNote: Note

  private val args: EditFragmentArgs by navArgs()

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    // Inflate the layout for this fragment
    editNoteBinding = FragmentEditBinding.inflate(inflater, container, false)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    val menuHost: MenuHost = requireActivity()
    menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

    notesViewModel = (activity as MainActivity).noteViewModel
    currentNote = args.note!!

    binding.editNoteTitle.setText(currentNote.noteTitle)
    binding.editNoteDescription.setText(currentNote.noteDescription)

    binding.editNoteFab.setOnClickListener {
      val noteTitle = binding.editNoteTitle.text.toString().trim()
      val noteDescription = binding.editNoteDescription.text.toString().trim()

      if (noteTitle.isNotEmpty()) {
        val note = Note(
          currentNote.id,
          noteTitle,
          noteDescription
        )
        notesViewModel.updateNote(note)
        view.findNavController().popBackStack(R.id.homeFragment, false)
      } else {
        Toast.makeText(view.context, "Note title cannot be empty", Toast.LENGTH_SHORT).show()
      }
    }
  }

  private fun deleteNote() {
    AlertDialog.Builder(requireContext()).apply {
      setTitle("Delete Note")
      setMessage("Are you sure you want to delete this note?")
      setPositiveButton("Yes") { _, _ ->
        notesViewModel.deleteNote(currentNote)
        Toast.makeText(context, "Note deleted successfully", Toast.LENGTH_SHORT).show()
        view?.findNavController()?.popBackStack(R.id.homeFragment, false)
      }
      setNegativeButton("No", null)
    }.create().show()
  }

  override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
    menu.clear()
    menuInflater.inflate(R.menu.edit_note, menu)
  }

  override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
    return when (menuItem.itemId) {
      R.id.delete_menu_note -> {
        deleteNote()
        true
      }

      else -> {
        false
      }
    }
  }

  override fun onDestroy() {
    super.onDestroy()
    editNoteBinding = null
  }

}