package ssc.snow.app.gomyways.data.model

data class ModelSearchedTripDetail(
    val `data`: Data? = Data(),
    val message: String,
    val status: Boolean
) {
    data class Data(
        val payment_details: List<PaymentDetail?>? = listOf(),
        val searchedTrip: Any? = Any(),
        val trip_id: String? = "",
        val trip_stop_points: Boolean? = false,
        val trip_stop_routes: Boolean? = false,
        val userId: String? = "",
        val viewTrip: Any? = Any(),
        val websiteSettings: WebsiteSettings? = WebsiteSettings()
    ) {
        data class PaymentDetail(
            val card_name: String? = "",
            val card_number: String? = "",
            val card_type: String? = "",
            val created_at: String? = "",
            val cvv: String? = "",
            val deleted_at: Any? = Any(),
            val expire_month: String? = "",
            val expire_year: String? = "",
            val id: String? = "",
            val updated_at: Any? = Any(),
            val user_id: Any? = Any()
        )

        data class WebsiteSettings(
            val created_at: String? = "",
            val deleted_at: Any? = Any(),
            val facebook_link: String? = "",
            val id: String? = "",
            val instagran_link: String? = "",
            val instant_booking_available: String? = "",
            val linkdin_link: String? = "",
            val price_per_km: String? = "",
            val refer_commission_amount: String? = "",
            val service_charges: String? = "",
            val updated_at: String? = "",
            val youtube_link: String? = ""
        )
    }
}