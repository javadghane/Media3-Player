package com.example.myapplication

import android.content.ComponentName
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import androidx.core.net.toUri
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
import com.example.myapplication.databinding.ActivityMainBinding
import com.google.common.util.concurrent.ListenableFuture
import com.google.common.util.concurrent.MoreExecutors
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@UnstableApi
class SecActivity : AppCompatActivity() {

    private val viewBinding by lazy(LazyThreadSafetyMode.NONE) {
        ActivityMainBinding.inflate(layoutInflater)
    }

    val musicOne = "https://dls.music-fa.com/tagdl/ali/Majid%20Razavi%20-%20Negine%20Ghalbami%20(320).mp3"
    val musicTwo = "https://dls.music-fa.com/tagdl/downloads/Ehaam%20-%20Cheshmanat%20Arezoost%20128.mp3"
    val musicThree = "https://dls.music-fa.com/tagdl/downloads/Ehaam%20-%20Taabo%20Tab%20(128).mp3"


    private lateinit var mediaController: MediaController


    /*
    *      if (mediaController.isPlaying) {
                mediaController.pause()
            } else {
                mediaController.play()
            }
            *
    * */


    override fun onStart() {
        super.onStart()


        val sessionToken = SessionToken(this, ComponentName(this, PlayerService::class.java))
        val controllerFuture = MediaController.Builder(this, sessionToken).buildAsync()
        controllerFuture.addListener(
            {
                mediaController = controllerFuture.get()!!
                //player = controllerFuture.get()
                //viewBinding.content.videoView.player = controllerFuture.get()
                viewBinding.content.videoView.player = mediaController




                mediaController.addListener(object : Player.Listener {
                    override fun onMediaMetadataChanged(mediaMetadata: androidx.media3.common.MediaMetadata) {
                        super.onMediaMetadataChanged(mediaMetadata)
                        /* binding.title.text = mediaMetadata.title
                         binding.author.text = mediaMetadata.artist
                         binding.loadingIndicator.isIndeterminate = false*/
                        CoroutineScope(Dispatchers.IO).launch {
                            /*     val url = URL(mediaMetadata.artworkUri.toString())
                                 val bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream())
                                 withContext(Dispatchers.Main) { binding.image.setImageBitmap(bitmap) }*/
                        }
                        android.util.Log.e("tag", "onMediaMetadataChanged" + mediaMetadata.title + "-" + mediaMetadata.artist)
                    }

                    override fun onIsPlayingChanged(isPlaying: Boolean) {
                        super.onIsPlayingChanged(isPlaying)
                        /*  if (isPlaying) binding.controlButton.setImageResource(R.drawable.pause_48px)
                          else binding.controlButton.setImageResource(R.drawable.play_arrow_48px)*/
                        android.util.Log.e("tag", "onIsPlayingChanged")
                    }

                    override fun onPlaybackStateChanged(playbackState: Int) {
                        super.onPlaybackStateChanged(playbackState)
                        android.util.Log.e("tag", "onPlaybackStateChanged")
                    }

                    override fun onPlayerError(error: PlaybackException) {
                        super.onPlayerError(error)
                        android.util.Log.e("tag", "onPlayerError=${error.stackTraceToString()}")
                    }

                    override fun onPlayerErrorChanged(error: PlaybackException?) {
                        super.onPlayerErrorChanged(error)
                        android.util.Log.e("tag", "onPlayerErrorChanged=${error?.stackTraceToString()}")
                    }
                })

            },
            MoreExecutors.directExecutor()
        )


    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(viewBinding.root)


        val startMediaServiceIntent = Intent(this, PlayerService::class.java)
        startService(startMediaServiceIntent)


        findViewById<Button>(R.id.btnPlay).setOnClickListener {


            mediaController.apply {


                /* val mmd = MediaMetadata.Builder()
                     .setTitle("Example")
                     .setArtist("Artist name")
                     .build()

                 val rmd = MediaItem.RequestMetadata.Builder()
                     .setMediaUri(musicOne.toUri())
                     .build()

                 val mediaItem = MediaItem.Builder()
                     .setMediaId("123")
                     .setMediaMetadata(mmd)
                     .setRequestMetadata(rmd)
                     .build()

                 setMediaItem(mediaItem)
                 prepare()
                 play()*/
            }


        }


    }


}