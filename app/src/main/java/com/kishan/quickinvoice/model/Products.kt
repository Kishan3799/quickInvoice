package com.kishan.quickinvoice.model

data class Products(
    val productId: String? = null,
    val productName:String? = null,
    val productSellingPrice: String? = null,
    val productQuantity:String? = null,
    val productQuantityUnit:String? = null,
    val productDiscount:String? = null,
    val productTax:String? = null
)
