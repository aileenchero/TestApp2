package com.example.testapp2



import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import android.view.Gravity
import android.view.View
import android.content.Intent
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_home.*

class Home : AppCompatActivity() {
    lateinit var toggle: ActionBarDrawerToggle
    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        auth = FirebaseAuth.getInstance()

        toggle = ActionBarDrawerToggle(this,drawable_layout, R.string.open, R.string.close)
        drawable_layout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        navView.setNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.UPLOAD -> {
                    Toast.makeText(
                        applicationContext,
                        "Click Item 1", Toast.LENGTH_SHORT
                    ).show()
                    val intent =Intent(this, UploadPhotos::class.java)
                    startActivity(intent)
                }
                R.id.inbox -> {
                    Toast.makeText(
                        applicationContext,
                        "Click Item 2", Toast.LENGTH_SHORT
                    ).show()
                    val intent =Intent(this, ChatBox::class.java)
                    startActivity(intent)
                }
                R.id.market-> {
                    Toast.makeText(
                        applicationContext,
                        "Click Item 3", Toast.LENGTH_SHORT
                    ).show()
                    val intent =Intent(this, ChatBox::class.java)
                    startActivity(intent)
                }
                R.id.logOut -> {
                    Logout()
                    Toast.makeText(
                        applicationContext,
                        "Click Item 4", Toast.LENGTH_SHORT
                    ).show()
                    val intent =Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }
            }
            true
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }
    private fun Logout(){
        auth.signOut()
        finish()
    }
}








        

