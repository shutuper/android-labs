package com.example.android1

import android.content.res.Configuration
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.VideoView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var videoView: VideoView
    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var playButton: Button
    private lateinit var pauseButton: Button
    private lateinit var stopButton: Button
    private lateinit var chooseButton: Button
    private lateinit var filePathTextView: TextView
    private lateinit var mediaUri: Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mediaPlayer = MediaPlayer()

        videoView = findViewById(R.id.videoView)
        videoView.layoutParams = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, 800)

        playButton = findViewById(R.id.playButton)
        pauseButton = findViewById(R.id.pauseButton)
        stopButton = findViewById(R.id.stopButton)
        chooseButton = findViewById(R.id.chooseButton)
        filePathTextView = findViewById(R.id.filePathTextView)

        playButton.setOnClickListener(this)
        pauseButton.setOnClickListener(this)
        stopButton.setOnClickListener(this)
        chooseButton.setOnClickListener {
            openMediaLauncher.launch("*/*")
        }

    }

    override fun onClick(v: View) {
        val isVideo = mediaUri.toString().contains(".mp4")
        try {
            when (v.id) {
                R.id.playButton ->
                    if (mediaUri.toString().contains(".mp4")) {
                        mediaPlayer.stop()
                        mediaPlayer.release()
                        mediaPlayer = MediaPlayer()
                        playVideo()
                    } else playAudio()

                R.id.pauseButton ->
                    if (isVideo && videoView.isPlaying) {
                        videoView.pause()
                    } else if (isVideo) {
                        videoView.start()
                    } else if (mediaPlayer.isPlaying) {
                        mediaPlayer.pause()
                    } else {
                        mediaPlayer.start()
                    }

                R.id.stopButton ->
                    if (isVideo && videoView.isPlaying) {
                        videoView.stopPlayback()
                        videoView.setOnPreparedListener(null)
                        videoView.setOnCompletionListener(null)
                        videoView.setOnErrorListener(null)
                        videoView.suspend()
                    } else if (mediaPlayer.isPlaying) {
                        mediaPlayer.stop()
                        mediaPlayer.release()
                        mediaPlayer = MediaPlayer()
                    }

            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun playVideo() {
        videoView.setVideoURI(mediaUri)
        videoView.start()
    }

    private fun playAudio() {
        try {
            mediaPlayer.setDataSource(applicationContext, mediaUri)
            mediaPlayer.prepare()
            mediaPlayer.start()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private val openMediaLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            if (uri != null && uri.toString().matches(Regex(".*\\.mp[3|4]"))) {
                mediaUri = uri
                val path = mediaUri.path.toString()
                filePathTextView.text = path.substring(path.lastIndexOf('/') + 1)
            }
        }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // Горизонтальна орієнтація екрану
            videoView.layoutParams = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT)
        } else {
            // Вертикальна орієнтація екрану
            videoView.layoutParams = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, 800)
        }
    }


}
