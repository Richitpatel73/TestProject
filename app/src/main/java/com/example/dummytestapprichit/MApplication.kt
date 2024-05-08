package com.example.dummytestapprichit

import android.app.Application
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley

class MApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        AppObject.initSingleton(this)
    }

    object AppObject {
        private lateinit var appContext: MApplication

        fun initSingleton(context: MApplication) {
            appContext = context
            // Perform any initialization tasks for the singleton here
        }

        fun getAppContext(): MApplication {
            return appContext
        }

        fun getRequestQueue() : RequestQueue {
            return Volley.newRequestQueue(getAppContext())
        }
    }
}
