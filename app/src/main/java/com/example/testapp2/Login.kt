package com.example.testapp2
import android.content.Intent

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_main.*

class Login : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    lateinit var database: DatabaseReference

    private var fUserId: String = " "

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()

        login_btn.setOnClickListener {
            loginUser()

        }
        sign_up.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

    }
    private fun loginUser() {
//        get values
        val email = editTextEmail.text.toString()
        val password = editTextPassword.text.toString()

        if (!email.isEmpty() && !password.isEmpty()){

            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener {task ->
                    if (task.isSuccessful){
//                        sign in success
                        auth.currentUser!!.uid

                        Toast.makeText(this, "Success", Toast.LENGTH_LONG).show()
                        val intent = Intent(this, Home::class.java)
                        startActivity(intent)



                    }else{
                        // If sign in fails, display a message to the user.
                        Toast.makeText(baseContext, "Authentication failed." + task.exception,
                            Toast.LENGTH_SHORT).show()

                    }
                }
        }
    }
}

