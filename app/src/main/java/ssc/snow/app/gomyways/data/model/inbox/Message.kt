package ssc.snow.app.gomyways.data.model.inbox

data class Message(
        val conversation_id: String? = "",
        val created_at: String? = "",
        val deleted_at: Any? = Any(),
        val `file`: Any? = Any(),
        val file_path: Any? = Any(),
        val fromUser: String? = "",
        val fromUserImg: String? = "",
        val fromUser_firstName: String? = "",
        val fromUser_lastName: String? = "",
        val from_id: String? = "",
        val from_image_url: String? = "",
        val id: String? = "",
        val message: String? = "",
        val seen: String? = "",
        val time: String? = "",
        val time_string: String? = "",
        val toUser: String? = "",
        val toUserImg: String? = "",
        val toUser_firstName: String? = "",
        val toUser_lastName: String? = "",
        val to_id: String? = "",
        val to_image_url: String? = "",
        val updated_at: Any? = Any()
)