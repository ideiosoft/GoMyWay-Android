package ssc.snow.app.gomyways.data.model

data class ModelSavedBankCards(
        val `data`: Data? = Data(),
        val message: String? = "",
        val status: Boolean? = false
) {
    data class Data(
            val account_number: String? = "",
            val bank_code: String? = "",
            val bank_user_name: String? = "",
            val bank_name: String? = ""
    )
}