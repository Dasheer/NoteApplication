package com.technovice.appnote

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.technovice.appnote.database.NoteDatabase
import com.technovice.appnote.repository.NoteRepository
import com.technovice.appnote.viewmodel.NoteViewModel
import com.technovice.appnote.viewmodel.NoteViewModelFactory

class MainActivity : AppCompatActivity() {

  private lateinit var noteViewModel: NoteViewModel

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    setupViewModel()
  }

  private fun setupViewModel() {
    val noteRepository = NoteRepository(NoteDatabase(this))

    val viewModelProviderFactory = NoteViewModelFactory(application, noteRepository)
    noteViewModel = ViewModelProvider(this, viewModelProviderFactory)[NoteViewModel::class.java]
  }
}