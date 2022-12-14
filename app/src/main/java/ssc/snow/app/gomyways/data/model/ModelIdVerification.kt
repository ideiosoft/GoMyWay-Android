package ssc.snow.app.gomyways.data.model

data class ModelIdVerification(
    val `data`: Data? = Data(),
    val message: String? = "",
    val status: Boolean
) {
    data class Data(
        val created_at: String? = "",
        val deleted_at: String? = "",
        val first_name_id: String? = "",
        val id: String? = "",
        val identity_proof: String? = "",
        val identity_proof_url: String? = "",
        val last_name_id: String? = "",
        val status: String? = "",
        val updated_at: String? = "",
        val user_id: String? = "",
        val username: String? = ""
    )
}