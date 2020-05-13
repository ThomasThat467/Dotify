package com.thatt.dotify

import android.app.Application
import com.thatt.dotify.manager.ApiManager
import com.thatt.dotify.manager.MusicManager

class DotifyApp: Application() {
    var musicManager: MusicManager = MusicManager()
    var apiManager: ApiManager = ApiManager()
}