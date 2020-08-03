package com.example.testapp2


import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import kotlinx.android.synthetic.main.activity_main.*
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.*


class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    lateinit var database: DatabaseReference

    private var fUserID: String = ""
    var callbackManager : CallbackManager? = null




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        auth = FirebaseAuth.getInstance()

        sign_up_btn.setOnClickListener {
            writeNewUser()
        }

        Sign_up.setOnClickListener {
            startActivity(Intent(this, Login::class.java))
        }
        facebook_sign_in_button.setOnClickListener {
            facebookLogin()
        }


        //printHashKey()
        callbackManager = CallbackManager.Factory.create()

    }





    private fun writeNewUser() {

        val username = editTextTextUsername.text.toString()
        val email = editTextTextEmail.text.toString()
        val password = editTextTextPassword.text.toString()

        if (!username.isEmpty() && !email.isEmpty() && !password.isEmpty()) {
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->

                    if (task.isSuccessful){
                        //                  sign in success update ui
                        Log.d("success", "create user with email is successful")

                        fUserID = auth.currentUser!!.uid
//                        database = FirebaseDatabase.getInstance().reference.child("dbfire").child(fUserID)
                        Toast.makeText(this, "Sucess", Toast.LENGTH_LONG).show()
                        val intent = Intent(this, Home::class.java)
                        startActivity(intent)




                        updateUi()
                    }else {
                        Log.w("failure", "Create user with email failed")

                        Toast.makeText(baseContext, "Authentication failed." + task.exception!!.message,
                            Toast.LENGTH_SHORT).show()
//                        updateUi(null)
                    }
                }

        }else {
            Toast.makeText(this,"Please fill up the Credentials :|", Toast.LENGTH_LONG).show()
        }


    }

    private fun updateUi() {
        editTextTextUsername.setText("")
        editTextTextEmail.setText("")
        editTextTextPassword.setText("")
    }
    fun facebookLogin(){
        LoginManager.getInstance()
            .logInWithReadPermissions(this, Arrays.asList("public_profile","email"))
        LoginManager.getInstance()
            .registerCallback(
                callbackManager,
                object : FacebookCallback<LoginResult> {
                    override fun onSuccess(result: LoginResult?) {
                        handleFacebookAccessToken(result?.accessToken)
                    }

                    override fun onCancel() {

                    }

                    override fun onError(error: FacebookException?) {

                    }
                })
    }

    fun handleFacebookAccessToken(token : AccessToken?){
        var credential = FacebookAuthProvider.getCredential(token?.token!!)
        auth?.signInWithCredential(credential)
            ?.addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    moveMainPage(task.result?.user)
                    val intent = Intent(this, Home::class.java)
                    startActivity(intent)

                }else{
                    Toast.makeText(this, task.exception?.message,Toast.LENGTH_LONG).show()

                }
            }


    }

    private fun moveMainPage(user: FirebaseUser?) {

    }


        override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        callbackManager?.onActivityResult(requestCode,resultCode, data)
    }







}

