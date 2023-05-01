package com.kishan.quickinvoice.model

/* customer Model */
data class CustomerModel(
    val customerName : String? =null,
    val customerEmailAddress : String? = null,
    val customerPhoneNumber : String? = null,
    val customerAddress : String? = null,
    val customerAdditionalInfo : String? = null
) {
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "customerName" to customerName,
            "customerEmailAddress" to customerEmailAddress,
            "customerPhoneNumber" to customerPhoneNumber,
            "customerAddress" to customerAddress,
            "customerAdditionalInfo" to customerAdditionalInfo,
        )
    }
}


