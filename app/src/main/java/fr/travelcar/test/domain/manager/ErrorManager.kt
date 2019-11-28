package fr.travelcar.test.domain.manager

import android.content.Context
import fr.travelcar.test.R
import java.net.ConnectException
import java.net.UnknownHostException

class ErrorManager(private val context: Context) {

    fun getErrorMessage(throwable: Throwable): String {
        return when (throwable) {
            is ConnectException -> context.getString(R.string.network_error_message) ?: ""
            is UnknownHostException -> context.getString(R.string.network_error_message) ?: ""
            else -> throwable.message ?: context.getString(R.string.unknown_error_message) ?: ""
        }
    }


    fun getErrorMessageFrom(apiError: ApiError): String = apiError.getErrorMessage(context)

    fun getDefaultErrorMessage(apiError: ApiError): String =
        context.getString(apiError.defaultDescriptionResId)


    sealed class ApiError(var defaultDescriptionResId: Int, private var reason: String? = null) {
        object NoNetwork : ApiError(R.string.no_network_error)
        object Timeout : ApiError(R.string.timeout)
        object NetworkError : ApiError(R.string.network_error)
        object UnexpectedApiResponse : ApiError(R.string.unexpected_response)

        fun getErrorMessage(context: Context) = reason ?: context.getString(defaultDescriptionResId)
    }
}