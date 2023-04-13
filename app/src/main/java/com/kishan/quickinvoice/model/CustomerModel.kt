package com.kishan.quickinvoice.model

data class CustomerModel(
    val customerName : String? =null,
    val customerEmailAddress : String? = null,
    val customerPhoneNumber : String? = null,
    val customerAddress : String? = null,
    val customerAdditionalInfo : String? = null
)