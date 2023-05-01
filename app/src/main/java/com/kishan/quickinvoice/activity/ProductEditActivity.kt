package com.kishan.quickinvoice.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.kishan.quickinvoice.R
import com.kishan.quickinvoice.databinding.ActivityProductEditBinding
import com.kishan.quickinvoice.model.Products

class ProductEditActivity : AppCompatActivity() {
    private lateinit var binding:ActivityProductEditBinding
    private lateinit var auth:FirebaseAuth
    private lateinit var database: DatabaseReference
    private lateinit var unit:String
    private var productName:String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductEditBinding.inflate(layoutInflater)
        setContentView(binding.root)
        database = FirebaseDatabase.getInstance().reference
        auth = FirebaseAuth.getInstance()

        productName = intent.getStringExtra("productName")

        //for closing activity
        binding.closeBtn.setOnClickListener {
            finish()
        }

        val unitItem = listOf("Pkt", "Kg", "Gram", "L", "Ml", "Box", "Pcs", "Dz", "Ft")
        val adapter = ArrayAdapter(this, R.layout.list_item, unitItem)
        binding.autoComplete.setAdapter(adapter)
        binding.autoComplete.setOnItemClickListener { adapterView, view, i, l ->
            unit = adapterView.getItemAtPosition(i).toString()
        }

        database.child("products").child(auth.currentUser!!.uid).child(productName!!)
            .addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                        if (snapshot.exists()){
                               binding.etProductEdit.setText(snapshot.child("productName").value.toString())
                               binding.etSellingPriceEdit.setText(snapshot.child("productSellingPrice").value.toString())
                               binding.etQuantityEdit.setText(snapshot.child("productQuantity").value.toString())
                               binding.etDiscountEdit.setText(snapshot.child("productDiscount").value.toString())
                               binding.etTaxEdit.setText(snapshot.child("productTax").value.toString())
                                // do units in autocomplete text
                        }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })

        binding.productUpdateBtn.setOnClickListener {
//            Log.d("PRODUCT_NAME", "$productName")
            val nameOfProduct = binding.etProductEdit.text.toString()
            val productPrice = binding.etSellingPriceEdit.text.toString()
            val productDiscount = binding.etDiscountEdit.text.toString()
            val productQuantity = binding.etQuantityEdit.text.toString()
            val productTax = binding.etTaxEdit.text.toString()
            val productUnit = binding.autoComplete.text.toString()
            updateProduct(nameOfProduct,productPrice,productQuantity,productUnit,productDiscount,productTax)
        }
    }

    private fun updateProduct(
        nameOfProduct: String,
        productPrice: String,
        productQuantity: String,
        productUnit: String,
        productDiscount: String,
        productTax: String,
    ) {
        val productModel = Products(
            productName = nameOfProduct,
            productSellingPrice = productPrice,
            productQuantity = productQuantity,
            productQuantityUnit = productUnit,
            productDiscount = productDiscount,
            productTax = productTax
        )
        val productValue = productModel.toMap()

        database.child("products").child(auth.currentUser!!.uid).child(productName!!).updateChildren(productValue)
            .addOnSuccessListener {
                Toast.makeText(this,"Inventory Updated Successfully", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener {
                Toast.makeText(this,"Failed to Update", Toast.LENGTH_SHORT).show()
            }
    }
}