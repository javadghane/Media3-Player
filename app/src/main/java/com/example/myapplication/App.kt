package com.example.myapplication

import android.app.Application
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.MediaSession
import androidx.media3.session.SessionToken

/*

MediaSession - exoplayer => service
SessionToken -> control MediaSession with MediaController
SessionCallback -> control media session from ui


* */

public class App : Application() {


    companion object{
        lateinit var player: ExoPlayer
    }

    override fun onCreate() {
        super.onCreate()
    }
}