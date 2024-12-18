package com.technovice.appnote.repository

import com.technovice.appnote.database.NoteDatabase
import com.technovice.appnote.model.Note

class NoteRepository (private val db: NoteDatabase) {

  suspend fun insert(note: Note) = db.getNoteDao().insert(note)
  suspend fun delete(note: Note) = db.getNoteDao().delete(note)
  suspend fun update(note: Note) = db.getNoteDao().update(note)

  fun getAllNotes() = db.getNoteDao().getAllNotes()
  fun searchNote (query: String?) = db.getNoteDao().searchNote(query)
}