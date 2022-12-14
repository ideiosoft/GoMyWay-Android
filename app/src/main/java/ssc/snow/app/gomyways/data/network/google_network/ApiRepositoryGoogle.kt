package ssc.snow.app.gomyways.data.network.google_network


class ApiRepositoryGoogle {

    private val apiInterfacefroGoogle: ApiInterfaceGoogle

    init {

        apiInterfacefroGoogle = ApiUtilsGoogle.getGoogleServices()
    }


    // ##############
    // Api's call
    // ##############
    suspend fun getRoutes(options: Map<String, String>?) =
            apiInterfacefroGoogle.getRoutes(options)

    suspend fun getRoutesStatic(options: Map<String, String>?) =
            apiInterfacefroGoogle.getRoutesStatic()


}