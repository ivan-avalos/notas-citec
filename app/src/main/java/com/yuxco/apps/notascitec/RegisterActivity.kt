package com.yuxco.apps.notascitec

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        btnRegister.setOnClickListener {
            if(etRegisterEmail.text.isNotEmpty() && etRegisterPassword.text.isNotEmpty()) {
                Shared.mAuth.createUserWithEmailAndPassword(etRegisterEmail.text.toString(), etRegisterPassword.text.toString())
                        .addOnCompleteListener {
                            if(it.isSuccessful) {
                                val intent = Intent(this, MainActivity::class.java)
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                startActivity(intent)
                            } else
                                Toast.makeText(this, it.exception?.localizedMessage, Toast.LENGTH_LONG).show()
                        }
            } else {
                Toast.makeText(this, getString(R.string.msg_empty_field), Toast.LENGTH_LONG).show()
            }
        }
    }
}
