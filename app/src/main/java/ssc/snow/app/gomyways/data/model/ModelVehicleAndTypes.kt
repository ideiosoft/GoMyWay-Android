package ssc.snow.app.gomyways.data.model

data class ModelVehicleAndTypes(
    val `data`: Data? = Data(),
    val message: String? = "",
    val status: Boolean
) {
    data class Data(
            val vehicle_types: ArrayList<VehicleType> = arrayListOf(),
            val vehicles: ArrayList<Vehicle> = arrayListOf()
    ) {
        data class VehicleType(
            val created_at: String? = "",
            val deleted_at: Any? = Any(),
            val id: String? = "",
            val updated_at: Any? = Any(),
            val vehicle_type: String? = ""
        )

        data class Vehicle(
            val created_at: String? = "",
            val deleted_at: Any? = Any(),
            val id: String? = "",
            val status: String? = "",
            val updated_at: Any? = Any(),
            val vehicle_name: String? = ""
        )
    }
}