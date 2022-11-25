package com.example.smssenderapplication.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.smssenderapplication.databinding.ActivityMainBinding
import com.example.smssenderapplication.util.ContactsExtractor

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.binding = ActivityMainBinding.inflate(this.layoutInflater)
        setContentView(binding.root)
        ContactsExtractor.extractContacts("والدین کانون", this)
    }
}