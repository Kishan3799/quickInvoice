package com.kishan.quickinvoice.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.kishan.quickinvoice.databinding.ActivityCustomerUpdateBinding
import com.kishan.quickinvoice.model.CustomerModel

class CustomerUpdateActivity : AppCompatActivity() {
    private lateinit var currentCustomerPhone: String
    private lateinit var binding : ActivityCustomerUpdateBinding
    private lateinit var database : DatabaseReference
    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCustomerUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference

        currentCustomerPhone = intent.getStringExtra("phone")!!
        Log.d("customerNumber" , currentCustomerPhone)
        binding.UpdateBtn.setOnClickListener {
           val name = binding.etCustomerName.text.toString()
           val email = binding.etCustomerEmail.text.toString()
           val phone = binding.etCustomerPhone.text.toString()
           val address = binding.etCustomerAddress.text.toString()
           val info = binding.etCustomerInformation.text.toString()

            updateCustomer(name, email, phone, address, info)

        }

        binding.closeBtn.setOnClickListener {
            finish()
        }


    }

    private fun updateCustomer(name: String, email: String, phone: String, address: String, info: String) {
        val customer = CustomerModel(name,email,phone,address,info)
        val customerValue = customer.toMap()
        database.child("customers").child(auth.currentUser!!.uid).child(currentCustomerPhone).updateChildren(customerValue)
            .addOnSuccessListener {
                Toast.makeText(this,"Customer Updated Successfully", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener {
                Toast.makeText(this,"Failed to Update", Toast.LENGTH_SHORT).show()
            }

    }
}