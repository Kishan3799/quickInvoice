package com.kishan.quickinvoice.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.kishan.quickinvoice.databinding.ActivityNewCustomerAddedBinding
import com.kishan.quickinvoice.fragment.CustomerFragment
import com.kishan.quickinvoice.model.CustomerModel

class NewCustomerAddedActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNewCustomerAddedBinding
    private lateinit var database: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewCustomerAddedBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = Firebase.database.reference

        with(binding){
            val name = this.etCustomerName.text
            val email = this.etCustomerEmail.text
            val phone = this.etCustomerPhone.text
            val address = this.etCustomerAddress.text
            val info = this.etCustomerInformation.text

            this.SaveBtn.setOnClickListener {
                this.etCustomerName.text!!.clear()
                this.etCustomerEmail.text!!.clear()
                this.etCustomerPhone.text!!.clear()
                this.etCustomerAddress.text!!.clear()
                this.etCustomerInformation.text!!.clear()
                addCustomer(name.toString(),email.toString(),address.toString(), phone.toString(), info.toString())
                Toast.makeText(this@NewCustomerAddedActivity, "Customer Added", Toast.LENGTH_SHORT).show()
            }

            this.closeBtn.setOnClickListener {
                finish()
            }
        }


    }

    private fun addCustomer(name:String, email:String, address:String, phoneNumber:String, info:String){
        val customer = CustomerModel(name,email,address,phoneNumber,info)
        database.child("customers").child(phoneNumber).setValue(customer)
    }
}