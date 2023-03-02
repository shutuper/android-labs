package com.example.android1

import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.VideoView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toFile
import java.io.IOException


class MainActivity : AppCompatActivity(), View.OnClickListener {
    private var videoView: VideoView? = null
    private var mediaPlayer: MediaPlayer? = null
    private var playButton: Button? = null
    private var pauseButton: Button? = null
    private var stopButton: Button? = null
    private var chooseButton: Button? = null
    private var filePathTextView: TextView? = null
    private var mediaUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        videoView = findViewById(R.id.videoView)
        playButton = findViewById(R.id.playButton)
        pauseButton = findViewById(R.id.pauseButton)
        stopButton = findViewById(R.id.stopButton)
        chooseButton = findViewById(R.id.chooseButton)
        filePathTextView = findViewById(R.id.filePathTextView)
        playButton!!.setOnClickListener(this)
        pauseButton!!.setOnClickListener(this)
        stopButton!!.setOnClickListener(this)
        chooseButton!!.setOnClickListener {
            openMediaLauncher.launch("*/*")
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.playButton -> if (mediaUri != null) {
                if (mediaUri.toString().contains(".mp4")) {
                    playVideo()
                } else {
                    playAudio()
                }
            }

            R.id.pauseButton -> if (mediaPlayer != null && mediaPlayer!!.isPlaying) {
                mediaPlayer!!.pause()
            }

            R.id.stopButton -> {
                if (mediaPlayer != null) {
                    mediaPlayer!!.stop()
                    mediaPlayer!!.release()
                    mediaPlayer = null
                }
                if (videoView != null) {
                    videoView!!.stopPlayback()
                }
            }

        }
    }

    private fun playVideo() {
        if (videoView != null) {
            videoView!!.setVideoURI(mediaUri)
            videoView!!.start()
        }
    }

    private fun playAudio() {
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer()
        }
        try {
            mediaPlayer!!.setDataSource(applicationContext, mediaUri!!)
            mediaPlayer!!.prepare()
            mediaPlayer!!.start()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private val openMediaLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            if (uri != null) {
                mediaUri = uri
                val path = mediaUri!!.path.toString()
                filePathTextView!!.text = path.substring(path.lastIndexOf('/') + 1)
            }
        }

}
