package com.kishan.quickinvoice.fragment

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.kishan.quickinvoice.R
import com.kishan.quickinvoice.databinding.FragmentServiceBinding
import com.kishan.quickinvoice.model.Services

class ServiceFragment:Fragment(R.layout.fragment_service) {
    private lateinit var binding: FragmentServiceBinding

    private lateinit var database: DatabaseReference
    private lateinit var auth: FirebaseAuth

    private var serviceName : Editable? = null
    private var serviceCharge : Editable? = null
    private var discount: Editable? = null
    private var tax: Editable? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentServiceBinding.inflate(layoutInflater)

        auth = FirebaseAuth.getInstance()
        database = Firebase.database.reference

        serviceName = binding.etService.text
        serviceCharge = binding.etServiceCharge.text
        discount = binding.etServiceDiscount.text
        tax = binding.etServiceTax.text

        binding.etServiceSaveBtn.setOnClickListener {
            addServicesToInventory(serviceName.toString(), serviceCharge.toString(), discount.toString(), tax.toString() )
            Toast.makeText(requireContext(), "Service Added" , Toast.LENGTH_SHORT).show()

            serviceName!!.clear()
            serviceCharge!!.clear()
            discount!!.clear()
            tax!!.clear()
        }

        return binding.root
    }

//    this function is add service to inventory firebase database
    private fun addServicesToInventory(serviceName: String, serviceCharge: String, discount: String, tax: String) {
        //it generate random id by default firebase database
        val serviceId = database.push().key

        val services = Services(serviceId,serviceName, serviceCharge, discount, tax)

        database.child("services").child(auth.currentUser!!.uid).child(serviceName).setValue(services)
    }
}