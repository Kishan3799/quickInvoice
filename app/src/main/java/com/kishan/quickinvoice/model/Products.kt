package com.kishan.quickinvoice.model

/* Products Model*/
data class Products(
    val productId: String? = null,
    val productName:String? = null,
    val productSellingPrice: String? = null,
    val productQuantity:String? = null,
    val productQuantityUnit:String? = null,
    val productDiscount:String? = null,
    val productTax:String? = null
) {
    fun toMap() : Map<String, Any?> {
        return mapOf(
            "productName" to productName,
            "productSellingPrice" to productSellingPrice,
            "productQuantity" to productQuantity,
            "productQuantityUnit" to productQuantityUnit,
            "productDiscount" to productDiscount,
            "productTax" to productTax
         )
    }
}


