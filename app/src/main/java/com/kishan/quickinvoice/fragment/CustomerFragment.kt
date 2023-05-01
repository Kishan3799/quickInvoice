package com.kishan.quickinvoice.fragment

import android.app.Application
import android.content.Context
import android.content.Intent
import android.os.Binder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.kishan.quickinvoice.R
import com.kishan.quickinvoice.activity.NewCustomerAddedActivity
import com.kishan.quickinvoice.adapter.CustomerAdapter
import com.kishan.quickinvoice.databinding.FragementCustomerBinding
import com.kishan.quickinvoice.model.CustomerModel

/* Customer Fragment have all customers to be added*/
class CustomerFragment:Fragment(R.layout.fragement_customer) {
    private lateinit var binding:FragementCustomerBinding
    private lateinit var database:FirebaseDatabase
    private lateinit var customerList:ArrayList<CustomerModel>
    private lateinit var customerAdapter: CustomerAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View{
        binding = FragementCustomerBinding.inflate(layoutInflater)

        // initialise firebaseDatabase instance
        database = FirebaseDatabase.getInstance()
        //initialise ArrayList to customer variable
        customerList = ArrayList()

        // initialise CustomerAdapter Class to customerAdapter variable
        customerAdapter = CustomerAdapter(requireContext(),customerList)

        // take reference customers to currentUserID to add all values in recyclerView using customer model class
        database.reference.child("customers").child(FirebaseAuth.getInstance().currentUser!!.uid)
            .addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    customerList.clear()

                    for (children in snapshot.children){
                        val customer = children.getValue(CustomerModel::class.java)
                        if (customer!!.customerPhoneNumber != FirebaseAuth.getInstance().uid){
                            customerList.add(customer)
                        }
                        binding.customerRv.adapter = customerAdapter
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })

        // Customer Fab button to add customer and open NewCustomerAddedActivity
        binding.addCustomerFabBtn.setOnClickListener{
            val intent = Intent(activity, NewCustomerAddedActivity::class.java)
            startActivity(intent)
        }

        return binding.root
    }
}