package com.chainreaction.sample.model.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.view.View
import android.view.View.OnClickListener
import android.view.WindowInsets
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import com.google.android.material.snackbar.Snackbar
import com.cainreaction.sample.R
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class AppUtils @Inject constructor(
    @ApplicationContext val context: Context
) {

    var loading: MutableLiveData<Boolean> = MutableLiveData(false)

    fun checkViews(vararg views: EditText): Boolean {
        for (ed in views) {
            if (ed.text.isNullOrBlank()) {
                ed.error = context.getString(R.string.empty_field)
                return false
            }
        }
        return true
    }


    fun showSnack(activity: Activity, message: String?) {
        val snackbar = Snackbar.make(
            activity.findViewById(android.R.id.content),
            message!!, Snackbar.LENGTH_LONG
        )
        snackbar.setActionTextColor(ContextCompat.getColor(activity, R.color.purple_200))
        snackbar.setAction(
            activity.getString(android.R.string.ok)
        ) { v: View? -> snackbar.dismiss() }
        snackbar.show()
    }

    fun showSnack(activity: Activity, message: String?, listener: OnClickListener) {
        val snackbar = Snackbar.make(
            activity.findViewById(android.R.id.content),
            message!!, Snackbar.LENGTH_LONG
        )
        snackbar.setActionTextColor(ContextCompat.getColor(activity, R.color.purple_200))
        snackbar.setAction(
            activity.getString(android.R.string.ok), listener
        )
        snackbar.show()
    }


    fun hideKeyboard(view: View) {


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val controller = view.windowInsetsController
            controller?.hide(WindowInsets.Type.ime())
        } else {
            val keyboardManager =
                context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            keyboardManager.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }


    @SuppressLint("MissingPermission")
    fun isInternetAvailable(context: Context): Boolean {

        var result = false
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val networkCapabilities = connectivityManager.activeNetwork ?: return false
            val actNw =
                connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
            result = when {
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            connectivityManager.run {
                connectivityManager.activeNetworkInfo?.run {
                    result = when (type) {
                        ConnectivityManager.TYPE_WIFI -> true
                        ConnectivityManager.TYPE_MOBILE -> true
                        ConnectivityManager.TYPE_ETHERNET -> true
                        else -> false
                    }

                }
            }
        }

        if (!result)
            Toast.makeText(
                context,
                "No internet connection, please check your connection",
                Toast.LENGTH_LONG
            ).show()


        return result
    }

    fun setLocale(activity: Activity, languageCode: String) {

        val resources = activity.resources
        val config = resources.configuration

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            config.setLocale(Locale(languageCode.lowercase(Locale.getDefault())))
        } else {

            val locale = Locale(languageCode)
            Locale.setDefault(locale)
            config.setLocale(locale)


            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N)
                context.createConfigurationContext(config)
        }

        resources.updateConfiguration(config, resources.displayMetrics)


    }


    fun createPartFromText(text: String): RequestBody {
        return text.toRequestBody(MultipartBody.FORM)
    }



}