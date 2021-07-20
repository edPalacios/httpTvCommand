package com.gompa.remotehttpcommand

import android.app.Application
import com.gompa.database.AppDatabase
import com.gompa.database.Database

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        Database(applicationContext)
    }
}
