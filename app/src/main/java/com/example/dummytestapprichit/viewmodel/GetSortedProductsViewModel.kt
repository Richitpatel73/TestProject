package com.example.dummytestapprichit.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dummytestapprichit.beans.Items
import com.example.dummytestapprichit.model.ApiEndPoints
import com.example.dummytestapprichit.model.NetworkRequestProcessor
import com.example.dummytestapprichit.model.RequestType
import com.google.gson.Gson
import org.json.JSONObject

class GetSortedProductsViewModel : ViewModel(), NetworkRequestProcessor.NetworkResponseListener {
    private var apiFailureStatusCode = MutableLiveData<Int?>()
    private var getSortedProductsLiveData = MutableLiveData<Items>()
    private var networkRequestProcessor: NetworkRequestProcessor

    init {
        apiFailureStatusCode = MutableLiveData()
        getSortedProductsLiveData = MutableLiveData()
        networkRequestProcessor = NetworkRequestProcessor(this)
    }

    fun getApiFailureObserver(): MutableLiveData<Int?> {
        return apiFailureStatusCode
    }

    fun getSortedProductsObserver(): MutableLiveData<Items> {
        return getSortedProductsLiveData
    }

    fun makeApiCall(userId: String, sortBy : String) {
        val json = JSONObject()
        json.put("userId", userId)
        json.put("sortBy", sortBy)
        json.put("authToken", "")

        val apiEndPoint = ApiEndPoints.GET_SORTED_PRODUCT_LIST
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
                getSortedProductsLiveData.postValue(items)
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