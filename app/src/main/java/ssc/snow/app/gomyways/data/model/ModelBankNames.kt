package ssc.snow.app.gomyways.data.model

data class ModelBankNames(
        val `data`: ArrayList<Data?>? = arrayListOf(),
        val message: String? = "",
        val status: Boolean? = false
) {
    data class Data(
        val name: String? = ""
    )
}