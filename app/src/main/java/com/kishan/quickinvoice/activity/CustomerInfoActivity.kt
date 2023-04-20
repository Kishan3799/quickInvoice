package com.kishan.quickinvoice.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.kishan.quickinvoice.databinding.ActivityCustomerInfoBinding
import com.kishan.quickinvoice.model.CustomerModel

class CustomerInfoActivity : AppCompatActivity() {
    private lateinit var binding : ActivityCustomerInfoBinding
    private lateinit var database : DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCustomerInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val contactNumber = intent.getStringExtra("phoneNumber")

        binding.backButton.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        binding.editButton.setOnClickListener {
            val openCustomerUpdateActivity = Intent(this, CustomerUpdateActivity::class.java)
            openCustomerUpdateActivity.putExtra("phone" , contactNumber)
            startActivity(openCustomerUpdateActivity)
        }
        binding.deleteButton.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Are you sure you want to delete this customer")
            builder.setPositiveButton("Delete"){ dialog,which->
                if(contactNumber != null) {
                    if (contactNumber.isNotEmpty()){
                        deleteCustomer(contactNumber)
                    }else{
                        Toast.makeText(this,  "Customer is not deleted ", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            builder.setNegativeButton("Cancel"){ dialog, which->
                dialog.cancel()
            }
            val alertDialog = builder.create()
            alertDialog.show()
        }


        database = FirebaseDatabase.getInstance().reference

        database.child("customers").child(FirebaseAuth.getInstance().currentUser!!.uid).child(contactNumber!!).get()
            .addOnSuccessListener { dataSnapshot ->
//                Log.d("firebase", "Got Value ${it.value}")
                if (dataSnapshot.exists()){
                    binding.customerTextName.text = dataSnapshot.child("customerName").value.toString()
                    binding.customerInfoEmailText.text = dataSnapshot.child("customerEmailAddress").value.toString()
                    binding.customerTextPhone.text = dataSnapshot.child("customerPhoneNumber").value.toString()
                    binding.customerTextAddress.text = dataSnapshot.child("customerAddress").value.toString()
                    binding.customerTextAdditionalInfo.text = dataSnapshot.child("customerAdditionalInfo").value.toString()
                    binding.customerName.text = dataSnapshot.child("customerName").value.toString()

                }
            }.addOnFailureListener {
//                Log.e("firebase" , "Error ", it)
                Toast.makeText(this, "Failed to Load..." , Toast.LENGTH_SHORT).show()
            }

    }

    private fun deleteCustomer(contactNumber: String) {
        database.child("customers").child(FirebaseAuth.getInstance().currentUser!!.uid).child(contactNumber).removeValue().addOnSuccessListener {
            Toast.makeText(this, "Customer is deleted", Toast.LENGTH_SHORT).show()
        }
    }
}
