package com.yuxco.apps.notascitec

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var adapter: NotesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        /* RecyclerView setup */
        rvNotes.layoutManager = LinearLayoutManager(this)
        adapter = NotesAdapter { view, noteId ->
            when (view.id) {
                R.id.cvNote -> {
                    val intent = Intent(this, ViewActivity::class.java)
                    intent.putExtra("id", noteId)
                    startActivity(intent)
                }
                R.id.btnNoteEdit -> {
                    val intent = Intent(this, AddActivity::class.java)
                    intent.putExtra("code", "EDIT")
                    intent.putExtra("id", noteId)
                    startActivity(intent)
                }
                R.id.btnNoteDelete -> {
                    AlertDialog.Builder(this)
                            .setTitle(R.string.title_delete)
                            .setMessage(R.string.msg_delete)
                            .setNegativeButton(R.string.title_delete) { dialog, which ->
                                Shared.mReference.child("users")
                                        .child(Shared.mAuth.currentUser?.uid!!).child(noteId).removeValue()
                                dialog.dismiss()
                            }.setPositiveButton(R.string.label_cancel) { dialog, which ->
                                dialog.dismiss()
                            }.create().show()
                }
            }
        }
        rvNotes.adapter = adapter

        /* Firebase Database setup */
        Shared.mReference.child("users").child(Shared.mAuth.currentUser?.uid!!)
                .addValueEventListener(object: ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                adapter.clearData()

                p0.children.forEach {
                    adapter.addNote(Note(
                            id = it.key!!,
                            title = it.child("title").value.toString(),
                            body = it.child("body").value.toString()))
                }
            }

            override fun onCancelled(p0: DatabaseError) {}
        })

        /* Floating action button */
        fab.setOnClickListener { view ->
            val intent = Intent(this, AddActivity::class.java)
            intent.putExtra("code", "ADD")
            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.ic_logout -> {
                Shared.mAuth.signOut()

                val intent = Intent (this, LoginActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
            else -> return super.onOptionsItemSelected(item)
        }

        return false
    }
}
