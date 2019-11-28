package fr.travelcar.test.domain.manager

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.telephony.TelephonyManager

class NetworkManager(private val context: Context) {
    fun getNetworkType(): CurrentNetworkType {
        val networkType =
            (context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager).networkType
        return when (networkType) {
            TelephonyManager.NETWORK_TYPE_GPRS,
            TelephonyManager.NETWORK_TYPE_EDGE,
            TelephonyManager.NETWORK_TYPE_CDMA,
            TelephonyManager.NETWORK_TYPE_1xRTT,
            TelephonyManager.NETWORK_TYPE_IDEN -> CurrentNetworkType.TYPE_2G

            TelephonyManager.NETWORK_TYPE_UMTS,
            TelephonyManager.NETWORK_TYPE_EVDO_0,
            TelephonyManager.NETWORK_TYPE_EVDO_A,
            TelephonyManager.NETWORK_TYPE_HSDPA,
            TelephonyManager.NETWORK_TYPE_HSUPA,
            TelephonyManager.NETWORK_TYPE_HSPA,
            TelephonyManager.NETWORK_TYPE_EVDO_B,
            TelephonyManager.NETWORK_TYPE_EHRPD,
            TelephonyManager.NETWORK_TYPE_HSPAP -> CurrentNetworkType.TYPE_3G

            TelephonyManager.NETWORK_TYPE_LTE -> CurrentNetworkType.TYPE_4G

            else -> CurrentNetworkType.UNKNOWN
        }
    }

    fun isDeviceConnected(): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        return activeNetwork?.isConnectedOrConnecting == true
    }

    enum class CurrentNetworkType(val currentType: String) {
        TYPE_2G("2G"),
        TYPE_3G("3G"),
        TYPE_4G("4G"),
        UNKNOWN("unknown")
    }
}