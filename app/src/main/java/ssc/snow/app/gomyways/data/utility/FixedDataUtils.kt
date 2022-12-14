package ssc.snow.app.gomyways.data.utility

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.text.Html
import androidx.fragment.app.Fragment
import org.jsoup.Jsoup
import java.util.*
import kotlin.collections.ArrayList


fun getYears(): ArrayList<String> {
    val years = ArrayList<String>()

    val thisYear = Calendar.getInstance().get(Calendar.YEAR)

    for (i in thisYear..thisYear + 20) {
        years.add(Integer.toString(i))
    }

    return years

}

fun getMonthsOfYear() = arrayOf("January", "Feburary", "March", "April",
        "May", "June", "July", "August", "September", "October", "November", "December")


fun getMonthId(mMonthName: String): String? {
    val mHashMap = hashMapOf("January" to "1", "Feburary" to "2", "March" to "3", "April" to "4", "May" to "5",
            "June" to "6", "July" to "7", "August" to "8", "September" to "9", "October" to "10", "November" to "11", "December" to "12")

    return mHashMap.get(mMonthName)
}


fun getCardTypes() = arrayOf("Visa", "Master Card", "American Express", "Discover", "Capital One")

fun html2text(html: String?) = Jsoup.parse(html).text()

fun getHtmlText(mData: String?): String? {
    return if (mData != null) {
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.M) {
            Html.fromHtml(mData).toString()
        } else {
            Html.fromHtml(mData, Html.FROM_HTML_MODE_LEGACY).toString()
        }
    } else ""
}


fun Fragment.shareMessage(shareBody: String = "") {
    if (shareBody.trim().isNotEmpty()) {
        val sharingIntent = Intent(Intent.ACTION_SEND)
        sharingIntent.type = "text/plain"
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Gomywayride App")
        sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody)
        startActivity(Intent.createChooser(sharingIntent, "Share Using"))
    }

//    if (mPhone.trim().isNotEmpty()) {
//        val smsIntent =
//            Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:$mPhone"))
//        //    smsIntent.setType("text");
//        smsIntent.putExtra(Intent.EXTRA_TEXT, shareBody)
//        //      smsIntent.setPackage("com.whatsapp");
//        startActivity(Intent.createChooser(smsIntent, "Share Using"))
//    }

}


fun Activity.shareMessage(shareBody: String = "") {
    if (shareBody.trim().isNotEmpty()) {
        val sharingIntent = Intent(Intent.ACTION_SEND)
        sharingIntent.type = "text/plain"
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Gomywayride App")
        sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody)
        startActivity(Intent.createChooser(sharingIntent, "Share Using"))
    }

//    if (mPhone.trim().isNotEmpty()) {
//        val smsIntent =
//                Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:$mPhone"))
//        //    smsIntent.setType("text");
//        smsIntent.putExtra(Intent.EXTRA_TEXT, shareBody)
//        //      smsIntent.setPackage("com.whatsapp");
//        startActivity(Intent.createChooser(smsIntent, "Share Using"))
//    }

}






