package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Handler
import android.os.Looper
import androidx.core.app.NotificationCompat
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.MediaNotification
import androidx.media3.session.MediaSession
import androidx.media3.session.MediaSessionService
import androidx.media3.session.MediaStyleNotificationHelper
import com.google.common.util.concurrent.Futures
import com.google.common.util.concurrent.ListenableFuture

@UnstableApi
class PlayerService : MediaSessionService(), MediaSession.Callback {

    private lateinit var player: ExoPlayer
    private var playerSession: MediaSession? = null
    lateinit var notificationManager: SimpleMediaNotificationManager

    val musicOne = "https://dls.music-fa.com/tagdl/ali/Majid%20Razavi%20-%20Negine%20Ghalbami%20(320).mp3"

    override fun onCreate() {
        super.onCreate()
        player = ExoPlayer.Builder(this).build()
        player.playWhenReady = true
        App.player = player
        playerSession = MediaSession.Builder(this, player).build()
        notificationManager = SimpleMediaNotificationManager(baseContext, player)

        /* val media = MediaItem.Builder().setUri(musicOne).build()
         player.addMediaItem(media)
         player.prepare()
         player.play()*/
    }

    override fun onGetSession(controllerInfo: MediaSession.ControllerInfo): MediaSession? = playerSession

    override fun onAddMediaItems(mediaSession: MediaSession, controller: MediaSession.ControllerInfo, mediaItems: MutableList<MediaItem>): ListenableFuture<MutableList<MediaItem>> {
        val updatedMediaItems = mediaItems.map { it.buildUpon().setUri(it.mediaId).build() }.toMutableList()
        notificationManager.startNotificationService(
            mediaSessionService = this,
            mediaSession = playerSession!!
        )
        return Futures.immediateFuture(updatedMediaItems)
    }


    @UnstableApi
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {


        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        playerSession?.run {
            player.release()
            release()
            playerSession = null
        }
    }

}