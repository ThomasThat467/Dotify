package com.thatt.dotify

import android.app.Application
import com.thatt.dotify.manager.ApiManager
import com.thatt.dotify.manager.MusicManager

class DotifyApp: Application() {
    lateinit var musicManager: MusicManager
    lateinit var apiManager: ApiManager

    override fun onCreate() {
        super.onCreate()

        musicManager = MusicManager()
        apiManager = ApiManager(this)
    }
}