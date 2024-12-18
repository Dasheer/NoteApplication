package com.technovice.appnote.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.technovice.appnote.databinding.LayoutNoteBinding
import com.technovice.appnote.fragment.HomeFragmentDirections
import com.technovice.appnote.model.Note

class NoteAdapter : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

  class NoteViewHolder(val itemBinding: LayoutNoteBinding) :
    RecyclerView.ViewHolder(itemBinding.root)

  private val differCallback = object : DiffUtil.ItemCallback<Note>() {
    override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
      return oldItem.id == newItem.id
              && oldItem.noteDescription == newItem.noteDescription
              && oldItem.noteTitle == newItem.noteTitle
    }

    override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
      return oldItem == newItem
    }
  }

  val differ = AsyncListDiffer(this, differCallback)

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
    return NoteViewHolder(
      LayoutNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )
  }

  override fun getItemCount(): Int {
    return differ.currentList.size
  }

  override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
    val currentNote = differ.currentList[position]

    holder.itemBinding.noteTitle.text = currentNote.noteTitle
    holder.itemBinding.noteDescription.text = currentNote.noteDescription

    holder.itemBinding.root.setOnClickListener {
      val direction = HomeFragmentDirections.actionHomeFragmentToEditFragment(currentNote)
      it.findNavController().navigate(direction)
    }
  }
}