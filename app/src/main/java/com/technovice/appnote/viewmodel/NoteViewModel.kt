package com.technovice.appnote.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.technovice.appnote.model.Note
import com.technovice.appnote.repository.NoteRepository
import kotlinx.coroutines.launch

class NoteViewModel(app : Application, private val noteRepository : NoteRepository) : AndroidViewModel(app) {

  fun addNote(note: Note) =
    viewModelScope.launch {
      noteRepository.insert(note)
    }

  fun deleteNote(note: Note) =
    viewModelScope.launch {
      noteRepository.delete(note)
    }

  fun updateNote(note: Note) =
    viewModelScope.launch {
      noteRepository.update(note)
    }

  fun getAllNotes() = noteRepository.getAllNotes()

  fun searchNotes(query: String?) = noteRepository.searchNote(query)
}