package com.kishan.quickinvoice.activity

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import androidx.fragment.app.Fragment
import com.kishan.quickinvoice.R
import com.kishan.quickinvoice.databinding.ActivityAddInventoryBinding
import com.kishan.quickinvoice.fragment.ProductFragment
import com.kishan.quickinvoice.fragment.ServiceFragment

class AddInventoryActivity : AppCompatActivity() {

    private lateinit var binding : ActivityAddInventoryBinding
    private lateinit var productFragment: ProductFragment
    private lateinit var serviceFragment: ServiceFragment
    private lateinit var motionEvent: MotionEvent
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddInventoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        productFragment = ProductFragment()
        serviceFragment = ServiceFragment()

        binding.backBtn.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
        setCurrentFragment(productFragment)

        binding.productButton.setBackgroundColor(Color.BLUE)
        binding.productButton.setTextColor(Color.WHITE)

        binding.productButton.setOnClickListener {
            setCurrentFragment(productFragment)
            binding.productButton.setBackgroundColor(Color.BLUE)
            binding.productButton.setTextColor(Color.WHITE)

            binding.serviceButton.setBackgroundColor(Color.TRANSPARENT)
            binding.serviceButton.setTextColor(Color.BLUE)
        }
        binding.serviceButton.setOnClickListener {
            setCurrentFragment(serviceFragment)
            binding.serviceButton.setBackgroundColor(Color.BLUE)
            binding.serviceButton.setTextColor(Color.WHITE)

            binding.productButton.setBackgroundColor(Color.TRANSPARENT)
            binding.productButton.setTextColor(Color.BLUE)
        }
    }

    private fun setCurrentFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.inventoryFragmentFrameLayout, fragment)
                .commit()
        }
    }
}