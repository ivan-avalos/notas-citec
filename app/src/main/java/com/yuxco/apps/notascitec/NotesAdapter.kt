package com.yuxco.apps.notascitec

import android.annotation.SuppressLint
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.note.view.*

class NotesAdapter(private val onClick: (view: View, noteId: String)->(Unit)): RecyclerView.Adapter<NotesAdapter.NotesViewHolder>() {

    /**
     * Dataset
     */

    val dataset = ArrayList<Note>()

    /**
     * Adapter functions
     */

    override fun getItemCount() = dataset.size

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(p0: NotesViewHolder, p1: Int) {
        val note = dataset.asReversed()[p1]

        p0.tvNoteTitle.text = note.title
        //p0.tvNoteBody.text = "${dataset[p1].body.substring(0..20)}..."
        p0.tvNoteBody.text = "${note.body.take(40)}..."

        p0.cvNote.setOnClickListener { it -> onClick(it, note.id) }
        p0.btnNoteEdit.setOnClickListener { it -> onClick (it, note.id) }
        p0.btnNoteDelete.setOnClickListener { it -> onClick(it, note.id) }
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): NotesViewHolder {
        val layInflater = LayoutInflater.from(p0.context).inflate(R.layout.note, p0, false)
        return NotesViewHolder(layInflater)
    }

    /**
     * Dataset functions
     */

    fun addNote(note: Note) {
        dataset.add(note)
        notifyDataSetChanged()
    }

    fun clearData() {
        dataset.clear()
        notifyDataSetChanged()
    }

    /**
     * View holder
     */

    class NotesViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val cvNote = view.cvNote

        val tvNoteTitle = view.tvNoteTitle
        val tvNoteBody = view.tvNoteBody

        val btnNoteEdit = view.btnNoteEdit
        val btnNoteDelete = view.btnNoteDelete
    }
}