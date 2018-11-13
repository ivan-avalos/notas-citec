package com.yuxco.apps.notascitec

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

import kotlinx.android.synthetic.main.activity_view.*
import kotlinx.android.synthetic.main.content_view.*

class ViewActivity : AppCompatActivity() {
    private lateinit var extras: Bundle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener {
            finish()
        }

        extras = intent.extras!!

        val note = Shared.mReference.child("users").child(Shared.mAuth.currentUser?.uid!!)
                .child(extras.getString("id")!!)

        note.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                tvViewTitle.text = p0.child("title").value.toString()
                tvViewBody.text = p0.child("body").value.toString()
            }

            override fun onCancelled(p0: DatabaseError) {}
        })

    }

}
