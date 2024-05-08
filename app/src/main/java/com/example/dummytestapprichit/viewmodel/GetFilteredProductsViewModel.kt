package com.example.dummytestapprichit.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dummytestapprichit.beans.Items
import com.example.dummytestapprichit.model.ApiEndPoints
import com.example.dummytestapprichit.model.NetworkRequestProcessor
import com.example.dummytestapprichit.model.RequestType
import com.google.gson.Gson
import org.json.JSONObject

class GetFilteredProductsViewModel : ViewModel(), NetworkRequestProcessor.NetworkResponseListener {
    private var apiFailureStatusCode = MutableLiveData<Int?>()
    private var getFilteredProductsLiveData = MutableLiveData<Items>()
    private var networkRequestProcessor: NetworkRequestProcessor

    init {
        apiFailureStatusCode = MutableLiveData()
        getFilteredProductsLiveData = MutableLiveData()
        networkRequestProcessor = NetworkRequestProcessor(this)
    }

    fun getApiFailureObserver(): MutableLiveData<Int?> {
        return apiFailureStatusCode
    }

    fun getFilteredProductsObserver(): MutableLiveData<Items> {
        return getFilteredProductsLiveData
    }

    fun makeApiCall(userId: String, filterQuery : String) {
        val json = JSONObject()
        json.put("userId", userId)
        json.put("filterQuery", filterQuery)
        json.put("authToken", "")

        val apiEndPoint = ApiEndPoints.GET_FILTERED_PRODUCT_LIST
        networkRequestProcessor.remoteRequest(
            requestType = RequestType.POST,
            apiEndPoint = apiEndPoint,
            requestObject = json
        )
    }

    override fun onApiSuccess(response: String?) {
        response?.let {
            try {
                val items : Items = Gson().fromJson(
                    response,
                    Items::class.java
                )
                getFilteredProductsLiveData.postValue(items)
            } catch (e: Exception) {
                val errorCode = 501
                apiFailureStatusCode.postValue(errorCode)
            }
        }
    }

    override fun onApiFailure(failure: Int?) {
        apiFailureStatusCode.postValue(failure)
    }
}