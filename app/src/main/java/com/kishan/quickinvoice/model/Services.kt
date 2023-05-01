package com.kishan.quickinvoice.model

/* Services Model*/
data class Services(
    val serviceId: String? = null,
    val serviceName:String? = null,
    val serviceCharge:String? = null,
    val serviceDiscount:String? = null,
    val serviceTax:String? = null
){
    fun toMap() : Map<String, Any?> {
        return mapOf(
            "serviceName" to serviceName,
            "serviceCharge" to serviceCharge,
            "serviceDiscount" to serviceDiscount,
            "serviceTax" to serviceTax
        )
    }
}
