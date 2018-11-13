package com.yuxco.apps.notascitec

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        if (Shared.mAuth.currentUser != null) {
            goToMainActivity()
        }

        btnLogin.setOnClickListener {
            if (etLoginEmail.text.isNotEmpty() && etLoginPassword.text.isNotEmpty()) {
                Shared.mAuth.signInWithEmailAndPassword(etLoginEmail.text.toString(),
                        etLoginPassword.text.toString()).addOnCompleteListener {
                    if(it.isSuccessful)
                        goToMainActivity()
                    else
                        Toast.makeText(this, it.exception?.localizedMessage, Toast.LENGTH_LONG).show()
                }
            } else {
                Toast.makeText(this, getString(R.string.msg_empty_field), Toast.LENGTH_LONG).show()
            }
        }

        btnRegisterLink.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    fun goToMainActivity () {
        val intent = Intent(this, MainActivity::class.java)
        /* Agregar flags para no poder regresar */
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

        startActivity(intent)
    }
}
