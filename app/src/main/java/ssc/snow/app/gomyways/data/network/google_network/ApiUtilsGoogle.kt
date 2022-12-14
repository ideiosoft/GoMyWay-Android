package ssc.snow.app.gomyways.data.network.google_network


object ApiUtilsGoogle {

    fun getGoogleServices(): ApiInterfaceGoogle {
        return RetrofitClientGoogle.getClient("https://maps.googleapis.com/maps/api/directions/")!!.create(ApiInterfaceGoogle::class.java)
    }
}
