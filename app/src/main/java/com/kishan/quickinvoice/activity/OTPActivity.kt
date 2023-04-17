package com.kishan.quickinvoice.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Email
import android.widget.Toast
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.kishan.quickinvoice.R
import com.kishan.quickinvoice.databinding.ActivityOtpactivityBinding
import java.util.concurrent.TimeUnit

class OTPActivity : AppCompatActivity() {
    private lateinit var binding:ActivityOtpactivityBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var verificationCode :String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOtpactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

        //this is get from registration activity
        val contactNumber = "+91"+intent.getStringExtra("number")
        binding.emailIdView.text = contactNumber

        //code sent on email id
        val option = PhoneAuthOptions.Builder(auth)
            .setPhoneNumber(contactNumber)
            .setTimeout(60L , TimeUnit.SECONDS)
            .setActivity(this)
            .setCallbacks(object : PhoneAuthProvider.OnVerificationStateChangedCallbacks(){
                override fun onVerificationCompleted(p0: PhoneAuthCredential) {
                    TODO("Not yet implemented")
                }

                override fun onVerificationFailed(error: FirebaseException) {
                    Toast.makeText(this@OTPActivity, "Please try Again $error", Toast.LENGTH_SHORT ).show()
                }

                override fun onCodeSent(code: String, token: PhoneAuthProvider.ForceResendingToken) {
                    super.onCodeSent(code, token)
                    verificationCode = code
                }
            }).build()
        PhoneAuthProvider.verifyPhoneNumber(option)

        //continue button
        binding.confirmBtn.setOnClickListener {
            val typedOtp = (binding.otpEt1.text.toString() + binding.otpEt2.text.toString() + binding.otpEt3.text.toString() +binding.otpEt4.text.toString() + binding.otpEt5.text.toString() + binding.otpEt6.text.toString())
            if (typedOtp.isEmpty()){
                Toast.makeText(this, "Please enter Otp", Toast.LENGTH_SHORT).show()
            }else{
                val credential = PhoneAuthProvider.getCredential(verificationCode, typedOtp)
                signInWithPhoneAuthCredential(credential)
            }

        }



    }
    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential){
        auth.signInWithCredential(credential)
            .addOnCompleteListener{
                if (it.isSuccessful){
                    startActivity(Intent(this, LoginActivity::class.java))
                    finish()
                }else{
                    Toast.makeText(this,"Error ${it.exception}", Toast.LENGTH_SHORT).show()
                }
            }
    }

}