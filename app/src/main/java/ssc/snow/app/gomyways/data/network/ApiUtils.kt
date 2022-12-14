package ssc.snow.app.gomyways.data.network


object ApiUtils {

    // URL :http://112.196.5.115/go_my_ways/api/authenticate/register   http://gomywayride.com/
    //  112.196.5.115 change To This IP 103.99.202.37
    fun getApiServices(): ApiInterface {
        return RetrofitClient.getClient("https://gomywayride.com/api/")!!.create(ApiInterface::class.java)
    }


}
