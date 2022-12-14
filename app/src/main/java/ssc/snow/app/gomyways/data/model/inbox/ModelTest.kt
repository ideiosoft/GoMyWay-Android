package ssc.snow.app.gomyways.data.model.inbox

data class ModelTest(
    val `data`: Data? = Data(),
    val message: String? = "",
    val status: Boolean? = false
) {
    data class Data(
        val account_number: String? = "",
        val bank_code: String? = ""
    )
}