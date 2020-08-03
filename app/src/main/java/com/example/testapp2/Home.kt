package com.example.testapp2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_home.*

class Home : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        photo.setOnClickListener {
            startActivity(Intent(this, ChatBox::class.java))
        }
        Chatbox.setOnClickListener {
            startActivity(Intent(this, ChatBox::class.java))
        }
    }
}