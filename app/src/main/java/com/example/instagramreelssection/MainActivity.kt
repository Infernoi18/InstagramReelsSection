package com.example.instagramreelssection

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2

class MainActivity : AppCompatActivity() {

    private lateinit var viewPager: ViewPager2
    private lateinit var adapter: VideoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewPager = findViewById(R.id.viewPager)

        val videos = listOf(
            R.raw.reel1,
            R.raw.reel2,
            R.raw.reel3,
            R.raw.reel4,
            R.raw.reel5
        )

        adapter = VideoAdapter(videos)
        viewPager.adapter = adapter

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                adapter.playVideo(position)
            }
        })

        viewPager.post {
            adapter.playVideo(0)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        adapter.releaseAll()
    }
}