package com.yuxco.apps.notascitec

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

object Shared {
    val mAuth = FirebaseAuth.getInstance()
    val mReference = FirebaseDatabase.getInstance().reference
}