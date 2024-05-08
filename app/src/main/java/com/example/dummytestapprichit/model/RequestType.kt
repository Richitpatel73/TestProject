package com.example.dummytestapprichit.model

import com.android.volley.Request

enum class RequestType(val requestMethodType: Int) {
    POST(Request.Method.POST),GET(Request.Method.GET)

}