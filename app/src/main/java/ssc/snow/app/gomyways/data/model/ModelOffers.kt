package ssc.snow.app.gomyways.data.model

data class ModelOffers(
    val `data`: List<Data?>? = listOf(),
    val message: String? = "",
    val status: Boolean
) {
    data class Data(
        val coupon_code: String? = "",
        val coupon_name: String? = "",
        val created_at: String? = "",
        val deleted_at: String? = "",
        val discount: String? = "",
        val discount_type: String? = "",
        val id: String? = "",
        val status: String? = "",
        val updated_at: String? = "",
        val valid_from: String? = "",
        val valid_to: String? = ""
    )
}