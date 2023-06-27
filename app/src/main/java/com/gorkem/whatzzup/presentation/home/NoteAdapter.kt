package com.gorkem.whatzzup.presentation.home

import android.annotation.SuppressLint
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.Timestamp
import com.gorkem.whatzzup.R
import com.gorkem.whatzzup.databinding.ItemNoteRowBinding
import com.gorkem.whatzzup.domain.model.Note



class NoteAdapter(
    private val fragment: Fragment,
    val listener: OnItemClickListener
) : RecyclerView.Adapter<NoteAdapter.ViewHolder>() {
     var noteList = mutableListOf<Note>()

    inner class ViewHolder(val binding: ItemNoteRowBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {
        init {
            binding.root.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                val note = noteList[position]
                listener.onItemClick(note)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemNoteRowBinding.inflate(fragment.layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount() = noteList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentNote = noteList[position]

        holder.binding.tvNoteTitle.text = currentNote.title
        holder.binding.tvNoteContent.text = currentNote.content
        Glide.with(fragment.requireContext())
            .load(currentNote.imageUrl)
            .placeholder(R.drawable.ic_image_placeholder)
            .into(holder.binding.ivNoteImage)

        holder.binding.tvUsername.text = currentNote.username
        holder.binding.tvNoteTimestamp.text = getRelativeTimestamp(currentNote.timeAdded!!)
    }

    private fun getRelativeTimestamp(timestamp: Timestamp): String {
        return DateUtils.getRelativeTimeSpanString(timestamp.seconds * 1000).toString()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setNotes(notes: List<Note>) {
        noteList.clear()
        noteList.addAll(notes)
        notifyDataSetChanged()
    }

    fun removeItem(position: Int) {
        noteList.removeAt(position)
        notifyItemRemoved(position)
    }


    interface OnItemClickListener {
        fun onItemClick(note: Note)
    }


}