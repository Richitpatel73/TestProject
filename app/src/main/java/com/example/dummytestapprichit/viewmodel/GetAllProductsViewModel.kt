package com.example.dummytestapprichit.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dummytestapprichit.beans.ArticleItem
import com.example.dummytestapprichit.beans.ArticleItems
import com.example.dummytestapprichit.beans.ItemNew
import com.example.dummytestapprichit.beans.Items
import com.example.dummytestapprichit.beans.UserItem
import com.example.dummytestapprichit.beans.UsersItems
import com.example.dummytestapprichit.model.ApiEndPoints
import com.example.dummytestapprichit.model.NetworkRequestProcessor
import com.example.dummytestapprichit.model.RequestType
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.launch
import org.json.JSONObject

class GetAllProductsViewModel : ViewModel(), NetworkRequestProcessor.NetworkResponseListener {
    private var apiFailureStatusCode = MutableLiveData<Int?>()
    private var getAllProductsLiveData = MutableLiveData<ArticleItems>()
    private var networkRequestProcessor: NetworkRequestProcessor

    init {
        apiFailureStatusCode = MutableLiveData()
        getAllProductsLiveData = MutableLiveData()
        networkRequestProcessor = NetworkRequestProcessor(this)
    }

    fun getApiFailureObserver(): MutableLiveData<Int?> {
        return apiFailureStatusCode
    }

    fun getAllProductsObserver(): MutableLiveData<ArticleItems> {
        return getAllProductsLiveData
    }

    fun makeApiCall(userId: String) {
        val json = JSONObject()
        json.put("userId", userId)
        json.put("authToken", "")

        val apiEndPoint = ApiEndPoints.GET_ALL_ARTICLES

        viewModelScope.launch {
            networkRequestProcessor.remoteRequestString(
                requestType = RequestType.GET,
                apiEndPoint = apiEndPoint,
                requestObject = json
            )
        }
    }

    override fun onApiSuccess(response: String?) {
        response?.let {
            try {
/*
                val items: Items = Gson().fromJson(
                    response,
                    Items::class.java
                )
*/

                val gson = Gson()
                val itemType = object : TypeToken<MutableList<ArticleItem>>() {}.type
                val itemList = gson.fromJson<MutableList<ArticleItem>>(response, itemType)
                val items = ArticleItems(itemList)
                getAllProductsLiveData.postValue(items)
            } catch (e: Exception) {
                val errorCode = 501
                apiFailureStatusCode.postValue(errorCode)
            }
        }
    }

    override fun onApiFailure(failure: Int?) {
        apiFailureStatusCode.postValue(failure)

        /*
                //sending hardcoded response
                val response = "{ \"items\": [ { \"name\": \"one\", \"id\": \"\", \"price\": 12, \"imageUrl\": \"\", \"brand\": \"Brand 1\" }, { \"name\": \"two\", \"id\": \"\", \"price\": 14, \"imageUrl\": \"\", \"brand\": \"Brand 2\" }, { \"name\": \"three\", \"id\": \"\", \"price\": 16, \"imageUrl\": \"\", \"brand\": \"Brand 3\" }, { \"name\": \"four\", \"id\": \"\", \"price\": 18, \"imageUrl\": \"\", \"brand\": \"Brand 4\" }, { \"name\": \"five\", \"id\": \"\", \"price\": 25, \"imageUrl\": \"\", \"brand\": \"Brand 5\" }, { \"name\": \"six\", \"id\": \"\", \"price\": 66, \"imageUrl\": \"\", \"brand\": \"Brand 6\" } ] }"
                response.let {
                    try {
                        val items : Items = Gson().fromJson(
                            response,
                            Items::class.java
                        )
                        getAllProductsLiveData.postValue(items)
                    } catch (e: Exception) {
                        val errorCode = 501
                        apiFailureStatusCode.postValue(errorCode)
                    }
                }
        */
    }
}