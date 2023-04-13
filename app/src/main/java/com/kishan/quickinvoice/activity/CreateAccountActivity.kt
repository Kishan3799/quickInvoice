package com.kishan.quickinvoice.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kishan.quickinvoice.databinding.ActivityCreateAccountBinding


class CreateAccountActivity : AppCompatActivity() {
    private lateinit var binding : ActivityCreateAccountBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //sign Up Button click to go create account activity
        binding.signupBtn.setOnClickListener {
          startActivity(Intent(this@CreateAccountActivity, RegistrationActivity::class.java))
        }

        //login Button click to go login activity to log the user in exist accounts
        binding.loginBtn.setOnClickListener {
            startActivity(Intent(this@CreateAccountActivity, LoginActivity::class.java))
        }
    }
}