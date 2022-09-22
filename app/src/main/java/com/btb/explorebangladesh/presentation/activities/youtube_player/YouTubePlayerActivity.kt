package com.btb.explorebangladesh.presentation.activities.youtube_player

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.btb.explorebangladesh.R
import com.btb.explorebangladesh.activity.findNavController
import com.btb.explorebangladesh.databinding.ActivityMainBinding
import com.btb.explorebangladesh.databinding.ActivityYouTubePlayerBinding
import com.btb.explorebangladesh.presentation.activities.base.BaseActivity
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener

class YouTubePlayerActivity : BaseActivity<ActivityYouTubePlayerBinding>() {



    override fun initializeViewBinding() = ActivityYouTubePlayerBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_you_tube_player)

        lifecycle.addObserver(binding.youtubePlayerView)
        binding.youtubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                youTubePlayer.loadVideo(extractVideoID(intent.getStringExtra("url")!!), 0f)
            }
        })
    }
}

fun extractVideoID( url:String) : String{
    Log.d("tag","url : $url")
    val video_id = url.split("v=")[1]
    val ampersandPosition = video_id.indexOf('&')
    if(ampersandPosition != -1) {
        return  video_id.substring(0, ampersandPosition)
    }
    else return video_id
}