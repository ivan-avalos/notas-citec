package com.yuxco.apps.notascitec

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener

import kotlinx.android.synthetic.main.activity_add.*
import kotlinx.android.synthetic.main.content_add.*

class AddActivity : AppCompatActivity() {
    private lateinit var extras: Bundle
    private lateinit var note: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        toolbar.setNavigationOnClickListener {
            finish()
        }

        extras = intent.extras!!

        if (extras.getString("code") == "EDIT") {
            note = Shared.mReference.child("users")
                    .child(Shared.mAuth.currentUser?.uid!!).child(extras.getString("id")!!)

            note.addListenerForSingleValueEvent(object: ValueEventListener {
                override fun onDataChange(p0: DataSnapshot) {

                    etTitle.setText(p0.child("title").value.toString())
                    etBody.setText(p0.child("body").value.toString())
                }

                override fun onCancelled(p0: DatabaseError) {}
            })
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_add, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId) {
            R.id.ic_add -> {
                if (extras.getString("code") == "ADD") {
                    val note = Shared.mReference.child("users").child(Shared.mAuth.currentUser?.uid!!).push()
                    note.child("title").setValue(etTitle.text.toString())
                    note.child("body").setValue(etBody.text.toString())
                } else if (extras.getString("code") == "EDIT") {
                    note.child("title").setValue(etTitle.text.toString())
                    note.child("body").setValue(etBody.text.toString())
                }
                finish()
            }
            else -> return super.onOptionsItemSelected(item)
        }
        return false
    }
}
