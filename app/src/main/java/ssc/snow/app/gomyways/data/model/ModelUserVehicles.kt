package ssc.snow.app.gomyways.data.model

data class ModelUserVehicles(
    val `data`: Data? = Data(),
    val message: String? = "",
    val status: Boolean
) {
    data class Data(
        val userVehicles: List<UserVehicle?>? = listOf()
    ) {
        data class UserVehicle(
            val created_at: String? = "",
            val deleted_at: Any? = Any(),
            val id: String? = "",
            val model: String? = "",
            val plate_number: String? = "",
            val updated_at: Any? = Any(),
            val user_id: Any? = Any(),
            val vehicle_id: String? = "",
            val vehicle_image_url: String? = "",
            val vehicle_name: String? ="",
            val vehicle_type: String? = "",
            val vehicle_type_id: String? = "",
            val vehicle_url: String? = "",
            val vehicle_user_id: String? = ""
        )
    }
}