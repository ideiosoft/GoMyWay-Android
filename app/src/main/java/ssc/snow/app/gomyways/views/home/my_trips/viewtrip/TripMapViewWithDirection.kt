package ssc.snow.app.gomyways.views.home.my_trips.viewtrip

import android.graphics.Color
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import kotlinx.android.synthetic.main.activity_trip_map_view_with_direction.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.json.JSONObject
import ssc.snow.app.gomyways.R
import ssc.snow.app.gomyways.data.model.ModelPostTripDetail
import ssc.snow.app.gomyways.data.utility.CommonActivity
import ssc.snow.app.gomyways.data.utility.InjectorUtil
import ssc.snow.app.gomyways.data.utility.google_decode.DirectionsJSONParser
import ssc.snow.app.gomyways.viewmodel.my_trips.ViewTripDetailViewModel


class TripMapViewWithDirection : CommonActivity(), OnMapReadyCallback {

    private lateinit var googleMap: GoogleMap

    private lateinit var mViewModel: ViewTripDetailViewModel
    private lateinit var mMapPoints: HashMap<String, String>

    val path: MutableList<List<LatLng>> = ArrayList()
    val mStopPoints = hashSetOf<String>()
    val mStopPointsForMarker = hashSetOf<LatLng>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trip_map_view_with_direction)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

        init()
    }

    private fun init() {
        mMapPoints = HashMap()

        // initialisae the view model
        mViewModel = ViewModelProviders.of(this).get(ViewTripDetailViewModel::class.java)

        img_back.setOnClickListener {
            this@TripMapViewWithDirection.finish()

        }


    }

    override fun onStart() {
        super.onStart()
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(Map: GoogleMap) {
        googleMap = Map
        googleMap.mapType = GoogleMap.MAP_TYPE_NORMAL

//   Add a marker in Sydney and move the camera
//   val sydney = LatLng(-34.0, 151.0)
//   mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
//   mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))

        var mWayPoints = ""

        CoroutineScope(context = IO).launch(Main) {

            if (InjectorUtil.tripDetail != null) {
                InjectorUtil.tripDetail?.apply {

                    mMapPoints.put("key", InjectorUtil.GOOGLE_API_KEY)
                    mMapPoints.put("origin", data!!.viewTrip!!.origin.toString())
                    mMapPoints.put("destination", data.viewTrip!!.destination.toString())

                    val mOriginLatLng = async {
                        val mlatLng = getLocationFromAddress(data.viewTrip.origin.toString())
                        LatLng(mlatLng.split(",")[0].toDouble(), mlatLng.split(",")[1].toDouble())

                    }.await()

                    val mDestinationLatLng = async {
                        val mlatLng = getLocationFromAddress(data.viewTrip.destination.toString())
                        LatLng(mlatLng.split(",")[0].toDouble(), mlatLng.split(",")[1].toDouble())

                    }.await()

// add origin and destination for the marker
                    mStopPointsForMarker.add(mOriginLatLng)
                    mStopPointsForMarker.add(mDestinationLatLng)
                    // add marker
                    googleMap.addMarker(MarkerOptions().position(mOriginLatLng).title(data.viewTrip.origin.toString()))
                    googleMap.addMarker(MarkerOptions().position(mDestinationLatLng).title(data.viewTrip.destination.toString()))

                    if (data.trip_stop_points!!.size > 0) {

                        for (mLD: ModelPostTripDetail.Data.TripStopPoint? in data.trip_stop_points) {

                            mStopPoints.add(mLD!!.stop_point.toString())

                            val mb = async {
                                val mlatLng = getLocationFromAddress(mLD.stop_point.toString())
                                LatLng(mlatLng.split(",")[0].toDouble(), mlatLng.split(",")[1].toDouble())

                            }.await()

                            // add the lat long long in the list
                            mStopPointsForMarker.add(mb)
                            googleMap.addMarker(MarkerOptions().position(mb).title(mLD.stop_point))

                        }

                        mWayPoints = mStopPoints.joinToString("|")

                        Log.wtf("mPonits", mWayPoints)
                    }

                    mMapPoints.put("waypoints", mWayPoints)
                    // animate the camera to the position
                    val cameraPosition = CameraPosition.Builder()
                            .target(mOriginLatLng)      // Sets the center of the map to Mountain View
                            .zoom(8f)                   // Sets the zoom
                            .bearing(0f)                // Sets the orientation of the camera to east
                            .tilt(30f)                   // Sets the tilt of the camera to 30 degrees
                            .build()

                    googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))


                }

                mViewModel.getRoutesData(mMapPoints).observe(this@TripMapViewWithDirection, Observer {
                    it?.let {
                        try {
                            ParserTask().execute(it.toString())

                        } catch (mExc: Exception) {
                            Log.wtf("error", mExc.message)
                        }

                    }
                })
            }


        }


    }


    private var mPolyline: Polyline? = null

    /** A class to parse the Google Directions in JSON format  */
    private inner class ParserTask : AsyncTask<String, Int, List<List<HashMap<String, String>>>>() {

        // Parsing the data in non-ui thread
        override fun doInBackground(vararg jsonData: String): List<List<HashMap<String, String>>>? {

            val jObject: JSONObject
            var routes: List<List<HashMap<String, String>>>? = null

            try {
                jObject = JSONObject(jsonData[0])
                val parser = DirectionsJSONParser()

                // Starts parsing data
                routes = parser.parse(jObject)
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return routes
        }

        // Executes in UI thread, after the parsing process
        override fun onPostExecute(result: List<List<HashMap<String, String>>>) {
            var points: ArrayList<LatLng>? = null
            var lineOptions: PolylineOptions? = null
            var mLatLng: LatLng? = null

            // Traversing through all the routes
            for (i in result.indices) {
                points = ArrayList()
                lineOptions = PolylineOptions()

                // Fetching i-th route
                val path = result[i]

                // Fetching all the points in i-th route
                for (j in path.indices) {
                    val point = path[j]

                    val lat = java.lang.Double.parseDouble(point["lat"].toString())
                    val lng = java.lang.Double.parseDouble(point["lng"].toString())
                    val position = LatLng(lat, lng)

                    points.add(position)
                    mLatLng = position


                }

                //  Adding all the points in the route to LineOptions
                lineOptions.addAll(points)
                lineOptions.width(10f)
                lineOptions.color(Color.BLUE)
            }

            // Drawing polyline in the Google Map for the i-th route
            if (lineOptions != null) {
                if (mPolyline != null) {
                    mPolyline!!.remove()
                }
                mPolyline = googleMap.addPolyline(lineOptions)

            } else
                Toast.makeText(applicationContext, "No route is found", Toast.LENGTH_LONG).show()
        }
    }
}


//                    // Get routes
//                    val routes: JsonObject = jsonResponse.getAsJsonArray("routes")[0] as JsonObject
//
//                    val legs = routes.getAsJsonArray("legs")[0] as JsonObject
//
//                    val steps: JsonArray = legs.getAsJsonArray("steps")
//
//                    for (i in 0 until steps.size()) {
//                        val pointsH: JsonObject = steps[i] as JsonObject     // getJSONObject("polyline").getString("points")

//                        val pointsh1 = pointsH.getAsJsonObject("polyline").get("points").toString() // getJSONObject("polyline").getString("points")
//
//                        path.add(PolyUtil.decode(pointsh1))
//                    }
//                    var rectOptions = PolylineOptions()
//
//                    Log.wtf("route_list", path.toString())
//
//                    for (i in 0 until path.size) {
//                        // this.googleMap.addPolyline(PolylineOptions().addAll(path[i]).width(6f).color(Color.RED))
//
//                        //  rectOptions.add(path[i])
//
//                        Log.wtf("route_", i.toString())
//
//                    }
//
//
//
//
//                    rectOptions = rectOptions.width(5f)
//                    rectOptions = rectOptions.color(Color.RED)
//                    val polyline = googleMap.addPolyline(rectOptions)
