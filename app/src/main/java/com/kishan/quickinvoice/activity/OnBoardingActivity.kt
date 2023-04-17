package com.kishan.quickinvoice.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.viewpager2.widget.ViewPager2
import com.kishan.quickinvoice.R
import com.kishan.quickinvoice.adapter.ViewPager2Adapter
import com.kishan.quickinvoice.databinding.ActivityOnBoardingBinding

class OnBoardingActivity : AppCompatActivity() {

    lateinit var viewPager2Adapter: ViewPager2Adapter
    lateinit var binding: ActivityOnBoardingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnBoardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewPager2Adapter = ViewPager2Adapter(this)
        binding.viewPager.adapter = viewPager2Adapter
        binding.dotsIndicator.attachTo(binding.viewPager)

        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int, ) {}

            override fun onPageSelected(position: Int) {
              if(position == 2){
                  binding.nextBtn.text = getText(R.string.started)
              }else {
                  binding.nextBtn.text = getText(R.string.next)
              }
            }
            override fun onPageScrollStateChanged(state: Int) {}
        })

        binding.nextBtn.setOnClickListener {
            if(getItem() > binding.viewPager.childCount){
//                Toast.makeText(this, "last Onboarding screen", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this@OnBoardingActivity, CreateAccountActivity::class.java))
            }else{
                binding.viewPager.setCurrentItem(getItem()+1, true)
            }
        }

        binding.skipBtn.setOnClickListener{
            if(getItem() == 0){
                Toast.makeText(this, "skip is click", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this@OnBoardingActivity, CreateAccountActivity::class.java))
            } else{
                binding.viewPager.setCurrentItem(getItem()-1, true)
            }
        }

    }

    private fun getItem(): Int {
        return binding.viewPager.currentItem
    }
}