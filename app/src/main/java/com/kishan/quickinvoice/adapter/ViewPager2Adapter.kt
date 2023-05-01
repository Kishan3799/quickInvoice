package com.kishan.quickinvoice.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kishan.quickinvoice.R
import com.kishan.quickinvoice.databinding.OnboardingsliderBinding

//OnBoarding Screen Adapter
class ViewPager2Adapter(private val context: Context) :RecyclerView.Adapter<ViewPager2Adapter.ViewPagerViewHolder>() {
    //viewPager viewHolder class extends with recyclerView holder class
    inner class ViewPagerViewHolder(val binding : OnboardingsliderBinding): RecyclerView.ViewHolder(binding.root)

    // override this function to create views and return type of this fun is viewPagerViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPagerViewHolder {
        //initialize layout to binding
        val binding = OnboardingsliderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewPagerViewHolder(binding)
    }

    //in this function return the size of the onBoarding list
    override fun getItemCount() = titleList.size

    //this function bind the viewPageViewHolder class to the layout
    override fun onBindViewHolder(holder: ViewPagerViewHolder, position: Int) {
        holder.binding.onBoardingImage.setImageResource(imagelist[position])
        holder.binding.onBoardingTitle.text = titleList[position]
        holder.binding.onBoardingSubtitle.text = subtitleList[position]
    }

    val imagelist = arrayOf(
        R.drawable.paid,
        R.drawable.invoice,
        R.drawable.recipt
    )

    val titleList = arrayOf(
        "Get paid faster",
        "Share invoice in Seconds",
        "Generate receipt quickly "
    )

    val subtitleList = arrayOf(
        "Share bank details and payment links with your customer",
        "Easily share invoice with your customer in no time",
        "Generate quick receipt for your business at any time"
    )


}