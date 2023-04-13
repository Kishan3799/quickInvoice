package com.kishan.quickinvoice.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.kishan.quickinvoice.databinding.ActivityRegistrationBinding
import com.kishan.quickinvoice.model.Users

class RegistrationActivity : AppCompatActivity() {
    private lateinit var binding : ActivityRegistrationBinding
    private lateinit var auth : FirebaseAuth
    private lateinit var database : DatabaseReference

    private  var businessName : Editable? = null
    private  var emailAddress : Editable? = null
    private  var phoneNumber : Editable? = null
    private  var createPassword : Editable? = null
    private  var reenterPassword : Editable? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth
        database = Firebase.database.reference

        businessName = binding.businessEt.text
        emailAddress = binding.emailEt.text
        phoneNumber = binding.phoneEt.text
        createPassword = binding.passwordEt.text
        reenterPassword = binding.reenterEt.text

        binding.continueBtn.setOnClickListener {
            Log.d("user", "$businessName $emailAddress $phoneNumber $createPassword $reenterPassword")
            registerUser(emailAddress.toString(), createPassword.toString())
            storeUser(businessName.toString(), emailAddress.toString(),phoneNumber.toString())
        }

        binding.loginViewBtn.setOnClickListener {
            startActivity(Intent(this@RegistrationActivity, LoginActivity::class.java))
        }

    }

//    this function is used to register user
       private fun registerUser(emailUser : String, passwordUser:String) {
        auth.createUserWithEmailAndPassword(emailUser, passwordUser)
            .addOnCompleteListener(this) {task ->
                if (task.isSuccessful){
                    Log.d("USERID", "createUserWithEmail:success")
                    val intent = Intent(this, OTPActivity::class.java)
                    intent.putExtra("number", binding.phoneEt.text!!.toString())
                    startActivity(intent)
                }else{
                    Toast.makeText(this, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                }
            }
       }

    //storing user Database
    private fun storeUser(businessName:String, emailAddress: String, phoneNumber: String ){
        val user = Users(businessName,emailAddress,phoneNumber)
        database.child("users").child(phoneNumber).setValue(user)
    }



}