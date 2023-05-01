package com.kishan.quickinvoice.fragment

import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.kishan.quickinvoice.R
import com.kishan.quickinvoice.databinding.FragmentProductBinding
import com.kishan.quickinvoice.model.Products
import kotlin.math.log

class ProductFragment:Fragment(R.layout.fragment_product) {
    private lateinit var binding : FragmentProductBinding
    private lateinit var database:DatabaseReference
    private lateinit var auth:FirebaseAuth
    private lateinit var unit:String

    private var products: Editable? = null
    private var sellingPrice: Editable? = null
    private var quantity: Editable? = null
    private var discount: Editable? = null
    private var tax: Editable? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentProductBinding.inflate(layoutInflater)

        auth = FirebaseAuth.getInstance()
        database = Firebase.database.reference

        products = binding.etProduct.text
        sellingPrice = binding.etSelling.text
        quantity = binding.etQuantity.text
        discount= binding.etDiscount.text
        tax= binding.etTax.text

        // list for choosing units
        val unitItem = listOf("Pkt", "Kg", "Gram", "L", "Ml", "Box", "Pcs", "Dz", "Ft")
        //initialise array adapter with listItemLayout  and unit item
        val adapter = ArrayAdapter(requireContext(), R.layout.list_item, unitItem)
        binding.autoComplete.setAdapter(adapter)
        binding.autoComplete.setOnItemClickListener { adapterView, view, i, l ->
            unit = adapterView.getItemAtPosition(i).toString()
        }

        // saving the product after click the productBtn
        binding.productBtn.setOnClickListener {
//            Log.d("product Unit", "$productName $sellingPrice $quantity $discount $tax $unit")
            addProductToInventory(
                products.toString(),
                sellingPrice.toString(),
                quantity.toString(),
                unit,
                discount.toString(),
                tax.toString()
            )
            Toast.makeText(requireContext(),"Product Added", Toast.LENGTH_SHORT).show()

            //clear text field after saving the product
            products!!.clear()
            sellingPrice!!.clear()
            quantity!!.clear()
            discount!!.clear()
            tax!!.clear()


        }


        return binding.root
    }

    //this function is add products to inventory firebase database
    private fun addProductToInventory(productName: String, sellingPrice: String, quantity: String, unit: String, discount: String, tax: String) {
        val productId = database.push().key
        val products = Products(productId, productName,sellingPrice, quantity, unit, discount, tax)
        database.child("products").child(auth.currentUser!!.uid).child(productName).setValue(products)
    }
}