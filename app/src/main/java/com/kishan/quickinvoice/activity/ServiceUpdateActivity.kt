package com.kishan.quickinvoice.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.kishan.quickinvoice.databinding.ActivityServiceUpdateBinding
import com.kishan.quickinvoice.model.Services

class ServiceUpdateActivity : AppCompatActivity() {
    private lateinit var binding: ActivityServiceUpdateBinding
    private var serviceName : String? = null
    private lateinit var database:DatabaseReference
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityServiceUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = FirebaseDatabase.getInstance().reference
        auth = FirebaseAuth.getInstance()

        serviceName = intent.getStringExtra("serviceName")

        binding.closeBtn.setOnClickListener {
            finish()
        }

        database.child("services").child(auth.currentUser!!.uid).child(serviceName!!)
            .addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()){
                        binding.etServiceName.setText(snapshot.child("serviceName").value.toString())
                        binding.etServiceChargeUpdate.setText(snapshot.child("serviceCharge").value.toString())
                        binding.etServiceDiscountUpdate.setText(snapshot.child("serviceDiscount").value.toString())
                        binding.etServiceTaxUpdate.setText(snapshot.child("serviceTax").value.toString())
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })

        binding.updateServiceBtn.setOnClickListener {
            val nameOfService = binding.etServiceName.text.toString()
            val serviceCharge = binding.etServiceChargeUpdate.text.toString()
            val serviceDiscount = binding.etServiceDiscountUpdate.text.toString()
            val serviceTax = binding.etServiceTaxUpdate.text.toString()

            updateService(nameOfService, serviceCharge, serviceDiscount,serviceTax)
        }
    }

    private fun updateService(nameOfService: String, serviceCharge: String, serviceDiscount: String, serviceTax: String) {
        val serviceModel = Services(
            serviceName = nameOfService,
            serviceCharge = serviceCharge,
            serviceDiscount = serviceDiscount,
            serviceTax = serviceTax
        )

        val serviceValue = serviceModel.toMap()

        database.child("services").child(auth.currentUser!!.uid).child(serviceName!!).updateChildren(serviceValue)
            .addOnSuccessListener {
                Toast.makeText(this,"Inventory Updated Successfully", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener {
                Toast.makeText(this,"Failed to Update", Toast.LENGTH_SHORT).show()
            }
    }
}