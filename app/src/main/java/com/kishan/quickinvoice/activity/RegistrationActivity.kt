package com.kishan.quickinvoice.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper

import android.text.Editable
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

import com.kishan.quickinvoice.databinding.ActivityRegistrationBinding
import com.kishan.quickinvoice.model.Users
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class RegistrationActivity : AppCompatActivity() {
    private lateinit var binding : ActivityRegistrationBinding

    private lateinit var auth : FirebaseAuth
    private lateinit var database : DatabaseReference

    private  var businessName : Editable? = null
    private  var emailAddress : Editable? = null
    private  var phoneNumber : Editable? = null
    private  var createPassword : Editable? = null
    private  var reenterPassword : Editable? = null


    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference

        businessName = binding.businessEt.text
        emailAddress = binding.emailEt.text
        phoneNumber = binding.phoneEt.text
        createPassword = binding.passwordEt.text
        reenterPassword = binding.reenterEt.text

        binding.continueBtn.setOnClickListener {
            Log.d("user", "$businessName $emailAddress $phoneNumber $createPassword $reenterPassword")
            registerUser(emailAddress.toString(), createPassword.toString())
            //todo this function is dealing 3 seconds then you will get current user id else you get null
            Handler(Looper.getMainLooper()).postDelayed({
                storeUserData(businessName.toString(), emailAddress.toString(),phoneNumber.toString())
            }, 3000)

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
   private fun storeUserData(businessName:String, emailAddress: String, phoneNumber: String ){
        val user = Users(
            auth.currentUser?.uid,
            businessName,
            emailAddress,
            phoneNumber
        )
//        database.child("users").child(phoneNumber).setValue(user)
        database.child("users").child(auth.currentUser!!.uid).setValue(user)
    }

}