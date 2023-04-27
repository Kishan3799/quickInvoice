package com.kishan.quickinvoice.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kishan.quickinvoice.R
import com.kishan.quickinvoice.databinding.ProductListItemBinding
import com.kishan.quickinvoice.databinding.ServiceListItemBinding
import com.kishan.quickinvoice.model.Products
import com.kishan.quickinvoice.model.Services

class InventoryAdapter(val context: Context, private var product: ArrayList<Products>, private var service: ArrayList<Services>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val PRODUCT = 1
    private val SERVICE = 2

    @SuppressLint("NotifyDataSetChanged")
    fun setLists(productsList: ArrayList<Products>, serviceList: ArrayList<Services>) {
        this.product = productsList
        this.service = serviceList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RecyclerView.ViewHolder {
//        return InventoryViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.inventory_item_rv_holder, parent,false))
        return when (viewType) {
            PRODUCT -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.product_list_item, parent, false)
                ProductViewHolder(view)
            }
            SERVICE -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.service_list_item, parent, false)
                ServiceViewHolder(view)
            }
            else -> throw java.lang.IllegalArgumentException("Invalid View Type")
        }
    }



    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            PRODUCT -> {
                val productIndex = product[position]
                (holder as ProductViewHolder)
                holder.binding.tvProduct.text = productIndex.productName
                holder.binding.tvProductPrice.text = productIndex.productSellingPrice
            }
            SERVICE -> {
                val serviceIndex = service[position - product.size]
                (holder as ServiceViewHolder)
                holder.binding.tvService.text = serviceIndex.serviceName
                holder.binding.tvServicePrice.text = serviceIndex.serviceCharge
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position < product.size){
            PRODUCT
        }else {
            SERVICE
        }
    }

    override fun getItemCount(): Int {
        return product.size + service.size
    }

//    inner class InventoryViewHolder(view:View) : RecyclerView.ViewHolder(view){
//        val binding:InventoryItemRvHolderBinding = InventoryItemRvHolderBinding.bind(view)
//    }

    inner class ProductViewHolder(view:View) : RecyclerView.ViewHolder(view){
        var binding = ProductListItemBinding.bind(view)
    }

    inner class ServiceViewHolder(view:View):RecyclerView.ViewHolder(view){
        var binding = ServiceListItemBinding.bind(view)
    }

}