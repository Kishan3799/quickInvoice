package com.kishan.quickinvoice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.kishan.quickinvoice.fragment.CustomerFragment
import com.kishan.quickinvoice.fragment.InventoryFragment
import com.kishan.quickinvoice.fragment.InvoiceFragment
import com.kishan.quickinvoice.fragment.ReceiptFragment

class MainActivity : AppCompatActivity() {
    private lateinit var invoiceFragment:Fragment
    private lateinit var receiptFragment:Fragment
    private lateinit var customerFragment:Fragment
    private lateinit var inventoryFragment:Fragment
    private lateinit var bottomNavigationView:BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //initialize fragment
        inventoryFragment = InventoryFragment()
        customerFragment = CustomerFragment()
        invoiceFragment = InvoiceFragment()
        receiptFragment = ReceiptFragment()

        setCurrentFragment(invoiceFragment)

        bottomNavigationView = findViewById(R.id.bottomBar)

        bottomNavigationView.setOnItemSelectedListener { item ->
            when(item.itemId){
                R.id.invoice->setCurrentFragment(invoiceFragment)
                R.id.receipt->setCurrentFragment(receiptFragment)
                R.id.customers->setCurrentFragment(customerFragment)
                R.id.inventory->setCurrentFragment(inventoryFragment)
            }
            true
        }

    }

    private fun setCurrentFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragLayout, fragment)
                .commit()
        }
    }
}