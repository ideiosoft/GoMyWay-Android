package ssc.snow.app.gomyways.data.model

data class ModelRideDetail(
    val `data`: Data? = Data(),
    val message: String? = "",
    val status: Boolean? = false
) {
    data class Data(
        val reviewDetail: List<ReviewDetail?>? = listOf(),
        val rideDetail: RideDetail? = RideDetail()
    ) {
        data class ReviewDetail(
            val DriverName: String? = "",
            val comment: String? = "",
            val created_at: String? = "",
            val deleted_at: Any? = Any(),
            val driver_id: String? = "",
            val id: String? = "",
            val passengerName: String? = "",
            val passenger_id: String? = "",
            val posted_trip_id: String? = "",
            val posted_trip_stops_id: String? = "",
            val rating: String? = "",
            val requested_trip_id: String? = "",
            val review_by: String? = "",
            val updated_at: Any? = Any()
        )

        data class RideDetail(
            val amount: String? = "",
            val approve_within: String? = "",
            val available_seats: String? = "",
            val back_row_seating: String? = "",
            val booking_preference: String? = "",
            val card_id: String? = "",
            val coupon_id: Any? = Any(),
            val created_at: String? = "",
            val deleted_at: Any? = Any(),
            val description: String? = "",
            val destination: String? = "",
            val discount_amount: String? = "",
            val driverEmail: String? = "",
            val driverFirst: String? = "",
            val driverLast: String? = "",
            val driver_id: String? = "",
            val event_id: Any? = Any(),
            val id: String? = "",
            val instructions: String? = "",
            val leaving: String? = "",
            val luggage: String? = "",
            val origin: String? = "",
            val others: String? = "",
            val passenger_id: String? = "",
            val posted_trip_id: String? = "",
            val posted_trip_stops_id: String? = "",
            val price_per_seat: String? = "",
            val requestStatus: String? = "",
            val request_id: String? = "",
            val returning: Any? = Any(),
            val ride_complete_date: Any? = Any(),
            val ride_requested_time: String? = "",
            val seats_booked: String? = "",
            val service_charges: String? = "",
            val status: String? = "",
            val stop_points: String? = "",
            val total_amount: String? = "",
            val total_seats: String? = "",
            val trip_id: String? = "",
            val trip_status: String? = "",
            val trip_type: String? = "",
            val updated_at: Any? = Any(),
            val user_vehicle_id: String? = "",
            val username: String? = "",
            val views: Any? = Any(),
            val driver_image_url: String? = String()
        )
    }
}