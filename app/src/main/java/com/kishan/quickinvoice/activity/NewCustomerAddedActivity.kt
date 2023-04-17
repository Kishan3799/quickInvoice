package com.kishan.quickinvoice.activity


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.kishan.quickinvoice.databinding.ActivityNewCustomerAddedBinding
import com.kishan.quickinvoice.model.CustomerModel

class NewCustomerAddedActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNewCustomerAddedBinding
    private lateinit var database: DatabaseReference
    private lateinit var auth: FirebaseAuth

    private var name : Editable? = null
    private var email : Editable? = null
    private var phone : Editable? = null
    private var address : Editable? = null
    private var info : Editable? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewCustomerAddedBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference

        name = binding.etCustomerName.text
        email = binding.etCustomerEmail.text
        phone = binding.etCustomerPhone.text
        address = binding.etCustomerAddress.text
        info = binding.etCustomerInformation.text

        binding.SaveBtn.setOnClickListener {
            addCustomer(name.toString(),email.toString(),address.toString(), phone.toString(), info.toString())
            Toast.makeText(this@NewCustomerAddedActivity, "Customer Added", Toast.LENGTH_SHORT).show()

            name!!.clear()
            email!!.clear()
            phone!!.clear()
            address!!.clear()
            info!!.clear()
        }

        binding.closeBtn.setOnClickListener { finish() }

    }

    private fun addCustomer(name:String, email:String, address:String, phoneNumber:String, info:String){
        val uniqueCustomerId = database.push().key
        val customer = CustomerModel(uniqueCustomerId,name,email,phoneNumber,address,info)
        database.child("customers").child(auth.currentUser!!.uid).child(uniqueCustomerId!!).setValue(customer)
    }
}