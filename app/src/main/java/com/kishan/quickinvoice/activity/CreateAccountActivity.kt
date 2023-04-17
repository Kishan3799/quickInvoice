package com.kishan.quickinvoice.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.kishan.quickinvoice.MainActivity
import com.kishan.quickinvoice.databinding.ActivityCreateAccountBinding


class CreateAccountActivity : AppCompatActivity() {
    private lateinit var binding : ActivityCreateAccountBinding
    private lateinit var auth:FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

        //sign Up Button click to go create account activity
        binding.signupBtn.setOnClickListener {
          startActivity(Intent(this@CreateAccountActivity, RegistrationActivity::class.java))
        }

        //login Button click to go login activity to log the user in exist accounts
        binding.loginBtn.setOnClickListener {
            startActivity(Intent(this@CreateAccountActivity, LoginActivity::class.java))
        }
    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if(currentUser != null){
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}