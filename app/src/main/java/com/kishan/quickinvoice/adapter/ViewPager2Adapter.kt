package com.kishan.quickinvoice.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kishan.quickinvoice.R
import com.kishan.quickinvoice.databinding.OnboardingsliderBinding

//OnBoarding Screen Adapter
class ViewPager2Adapter(private val context: Context) :RecyclerView.Adapter<ViewPager2Adapter.ViewPagerViewHolder>() {
    inner class ViewPagerViewHolder(val binding : OnboardingsliderBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPagerViewHolder {
        val binding = OnboardingsliderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewPagerViewHolder(binding)
    }

    override fun getItemCount() = titleList.size

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