package com.kishan.quickinvoice.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
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


        customerDetailsPreview(currentCustomerPhone)
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

    //this function update the customer detail
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

    //this function preview the detail of the customer in the editText Layout
    private fun customerDetailsPreview(phoneNumber:String?){
        database.child("customers").child(auth.currentUser!!.uid).child(phoneNumber!!)
            .addValueEventListener(object :ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()){
                        binding.etCustomerName.setText(snapshot.child("customerName").value.toString())
                        binding.etCustomerEmail.setText(snapshot.child("customerEmailAddress").value.toString())
                        binding.etCustomerPhone.setText(snapshot.child("customerPhoneNumber").value.toString())
                        binding.etCustomerAddress.setText(snapshot.child("customerAddress").value.toString())
                        binding.etCustomerInformation.setText(snapshot.child("customerAdditionalInfo").value.toString())
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                   Toast.makeText(this@CustomerUpdateActivity, "$error", Toast.LENGTH_SHORT).show()
                }

            })
    }

}