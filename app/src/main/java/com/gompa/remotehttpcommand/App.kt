package com.gompa.remotehttpcommand

import android.app.Application
import com.gompa.database.Database

class App : Application() {

    init {
        Database(this)
    }
}
