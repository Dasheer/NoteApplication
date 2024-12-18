package com.technovice.appnote.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.technovice.appnote.model.Note

@Dao
interface NoteDao {
  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insert(note: Note)

  @Update
  suspend fun update(note: Note)

  @Delete
  suspend fun delete(note: Note)

  @Query("SELECT * FROM NOTES ORDER BY id DESC")
  fun getAllNotes(): LiveData<List<Note>>

  @Query("SELECT * FROM NOTES WHERE noteTitle LIKE :query OR noteDescription LIKE :query")
  fun searchNote(query: String?): LiveData<List<Note>>
}