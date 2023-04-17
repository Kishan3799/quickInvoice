package com.kishan.quickinvoice.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.internal.TextDrawableHelper.TextDrawableDelegate
import com.kishan.quickinvoice.R
import com.kishan.quickinvoice.databinding.CustomerRvItemBinding
import com.kishan.quickinvoice.model.CustomerModel
import org.w3c.dom.Text

class CustomerAdapter(val context: Context, var customerList:ArrayList<CustomerModel> ) : RecyclerView.Adapter<CustomerAdapter.CustomerViewHolder>(){
    inner class CustomerViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val binding: CustomerRvItemBinding = CustomerRvItemBinding.bind(view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomerViewHolder {
        return CustomerViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.customer_rv_item, parent, false))
    }

    override fun getItemCount(): Int = customerList.size

    override fun onBindViewHolder(holder: CustomerViewHolder, position: Int) {
        val customer = customerList[position]

        holder.binding.tvNameFirstLetter.text = customer.customerName!![0].toString() //Alternatively, using customer.customerName!!.substring(0,1) return first letter of name
        holder.binding.tvName.text = customer.customerName
        holder.binding.tvContact.text = customer.customerPhoneNumber
        holder.binding.customerItemCard.setOnClickListener {
            Toast.makeText(context,"click on", Toast.LENGTH_SHORT).show()
        }
    }

}