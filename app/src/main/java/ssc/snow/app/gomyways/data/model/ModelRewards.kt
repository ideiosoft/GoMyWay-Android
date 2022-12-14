package ssc.snow.app.gomyways.data.model

data class ModelRewards(
    val `data`: Data? = Data(),
    val message: String? = "",
    val status: Boolean
) {
    data class Data(
        val total_earning: Int? = 0,
        val users: List<User?>? = listOf()
    ) {
        data class User(
            val commission_amount: String? = "",
            val created_at: String? = "",
            val deleted_at: String? = "",
            val id: String? = "",
            val referal_user_id: String? = "",
            val referralUser: String? = "",
            val referredUser: String? = "",
            val referred_user_id: String? = "",
            val updated_at: String? = "",
            val image_url: String? = ""

        )
    }
}