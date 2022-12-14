package ssc.snow.app.gomyways.data.model

data class ModelAllNotifications(
    val `data`: List<Data?>? = listOf(),
    val message: String? = "",
    val status: Boolean? = false
) {
    data class Data(
        val conversation_id: String? = "",
        val created_at: String? = "",
        val first_name: String? = "",
        val from_user_id: String? = "",
        val id: String? = "",
        val last_name: String? = "",
        val notification: String? = "",
        val notification_link: Any? = Any(),
        val profile_image_url: String? = "",
        val request_id: Any? = Any(),
        val time: String? = "",
        val time_created: String? = "",
        val time_string: Int? = 0,
        val updated_at: Any? = Any(),
        val user_id: String? = "",
        val username: String? = ""
    )
}