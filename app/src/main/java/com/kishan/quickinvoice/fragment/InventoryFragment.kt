package com.kishan.quickinvoice.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import com.kishan.quickinvoice.R
import com.kishan.quickinvoice.activity.AddInventoryActivity
import com.kishan.quickinvoice.adapter.InventoryAdapter
import com.kishan.quickinvoice.databinding.FragmentInventoryBinding
import com.kishan.quickinvoice.model.Inventory
import com.kishan.quickinvoice.model.Products
import com.kishan.quickinvoice.model.Services

class InventoryFragment:Fragment(R.layout.fragment_inventory) {
    private lateinit var binding: FragmentInventoryBinding
    private lateinit var inventoryAdapter: InventoryAdapter
    private lateinit var database:DatabaseReference
    private lateinit var productList : ArrayList<Products>
    private lateinit var serviceList : ArrayList<Services>
    private lateinit var auth: FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentInventoryBinding.inflate(layoutInflater)
        auth = FirebaseAuth.getInstance()
        productList = ArrayList()
        serviceList = ArrayList()

        database  = FirebaseDatabase.getInstance().reference
        inventoryAdapter = InventoryAdapter(requireContext(), productList, serviceList)

        database.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                productList.clear()
                serviceList.clear()

                for (child in snapshot.child("products").child(auth.currentUser!!.uid).children){
                    val product = child.getValue(Products::class.java)
                    product?.let {
                        productList.add(product)
                    }
                }
                for (child in snapshot.child("services").child(auth.currentUser!!.uid).children){
                    val service = child.getValue(Services::class.java)
                    service?.let {
                        serviceList.add(service)
                    }
                }

                inventoryAdapter.setLists(productList,serviceList)
                binding.inventoryRv.adapter = inventoryAdapter
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })


        binding.addInventoryFabBtn.setOnClickListener {
            val openAddInventoryActivity = Intent(context, AddInventoryActivity::class.java)
            startActivity(openAddInventoryActivity)
        }

        return binding.root
    }


}

