package com.example.dummytestapprichit.model

import android.content.Context
import android.util.Log
import com.android.volley.AuthFailureError
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.example.dummytestapprichit.MApplication
import com.example.dummytestapprichit.utils.Utils
import org.json.JSONObject

class NetworkRequestProcessor() {
    private val timeoutInMs = 110000
    private lateinit var networkResponseListener: NetworkResponseListener
    private lateinit var requestType: RequestType
    private lateinit var apiEndPoint: String
    private var requestObject: JSONObject? = null
    private val utils: Utils = Utils()
    private val context: Context = MApplication.AppObject.getAppContext()


    constructor(networkResponseListener: NetworkResponseListener) : this() {
        this.networkResponseListener = networkResponseListener
    }


    fun remoteRequest(
        requestType: RequestType,
        apiEndPoint: String,
        requestObject: JSONObject? = JSONObject()
    ) {
        this.requestType = requestType
        this.apiEndPoint = apiEndPoint
        this.requestObject = requestObject

        val baseURL = ""
        val requestUrl = baseURL + apiEndPoint
        if (utils.isInternetAvailable(context)) {
            val jsonRequest: JsonObjectRequest =
                object : JsonObjectRequest(requestType.requestMethodType,
                    requestUrl,
                    requestObject,
                    Response.Listener<JSONObject> { response ->
                        Log.i("Response", response.toString())
                        networkResponseListener.onApiSuccess(response.toString())
                    },
                    Response.ErrorListener { error ->
                        if (error?.networkResponse != null) {
                            Log.e("Error", error.message.toString())
                            networkResponseListener.onApiFailure(error.networkResponse.statusCode)
                        } else {
                            networkResponseListener.onApiFailure(404)
                        }
                    }) {
                    @Throws(AuthFailureError::class)
                    override fun getHeaders(): Map<String, String> {
                        val params: MutableMap<String, String> = HashMap()
                        params["Accept"] = "application/json"
                        return params
                    }
                }
            jsonRequest.retryPolicy = DefaultRetryPolicy(
                timeoutInMs,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
            )
            MApplication.AppObject.getRequestQueue().add(jsonRequest)
        } else {
            TODO("No Internet Available")
        }
    }

    fun remoteRequestString(
        requestType: RequestType,
        apiEndPoint: String,
        requestObject: JSONObject? = JSONObject()
    ) {
        this.requestType = requestType
        this.apiEndPoint = apiEndPoint
        this.requestObject = requestObject

        val baseURL = ""
        val requestUrl = baseURL + apiEndPoint
        if (utils.isInternetAvailable(context)) {
            val stringRequest: StringRequest = object : StringRequest(
                requestType.requestMethodType,
                requestUrl,
                Response.Listener<String> { response ->
                    networkResponseListener.onApiSuccess(response.toString())
                  },
                Response.ErrorListener { error ->
                    networkResponseListener.onApiFailure(error.networkResponse.statusCode) }
            ) {
                @Throws(AuthFailureError::class)
                override fun getHeaders(): Map<String, String> {
                    val params: MutableMap<String, String> = HashMap()
                    params["Accept"] = "application/json"
                    return params
                }

                override fun getParams(): MutableMap<String, String> {
                    val queryParams: MutableMap<String, String> = HashMap()
                    return queryParams
                }
            }
            stringRequest.retryPolicy = DefaultRetryPolicy(
                timeoutInMs,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
            )
            MApplication.AppObject.getRequestQueue().add(stringRequest)
        } else {
            TODO("No Internet Available")
        }
    }

    interface NetworkResponseListener {
        fun onApiSuccess(response: String?)
        fun onApiFailure(failure: Int?)
    }
}