package com.velaphi.tabby.ui.view

import android.content.res.Configuration
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.android.exoplayer2.DefaultRenderersFactory
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import com.velaphi.tabby.R
import com.velaphi.tabby.data.database.TabbyEntry
import com.velaphi.tabby.ui.view.MainAdapter.Companion.IMAGE
import kotlinx.android.synthetic.main.activity_tabby_details.*

class TabbyDetailsActivity : AppCompatActivity() {
    private lateinit var exoPlayer: SimpleExoPlayer
    private lateinit var eventLogger: EventLogger

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tabby_details)
        val data = intent.extras
        val tabbyEntry = data?.getParcelable<TabbyEntry>(IMAGE)

        if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            setContentView(R.layout.activity_tabby_details)
        } else {
            setContentView(R.layout.activity_tabby_details_landscape)
        }

        supportActionBar?.title = tabbyEntry?.title;
        play_me.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            initializeExoplayer()
        }}

        if (tabbyEntry != null) {
            description_textview.text = tabbyEntry.description
            val catImage = findViewById<ImageView>(R.id.imageView)
            Glide
                .with(this)
                .load(tabbyEntry.url)
                .centerCrop()
                .placeholder(R.drawable.ic_placeholder)
                .error(R.drawable.ic_error)
                .into(catImage)
        }
    }

    fun initializeExoplayer() {
        val renderersFactory = DefaultRenderersFactory(this,
            null, // drmSessionManager: DrmSessionManager
            DefaultRenderersFactory.EXTENSION_RENDERER_MODE_OFF)

        val trackSelector = DefaultTrackSelector()
        exoPlayer = ExoPlayerFactory.newSimpleInstance(renderersFactory, trackSelector)

        eventLogger = EventLogger(trackSelector)
        exoPlayer.addListener(eventLogger)
        exoPlayer.setAudioDebugListener(eventLogger)
        exoPlayer.setMetadataOutput(eventLogger)

        val userAgent = Util.getUserAgent(this, "Tabby")
        val mediaSource = ExtractorMediaSource(
            Uri.parse("asset:///Meowing-cat-sound.mp3"),
            DefaultDataSourceFactory(this, userAgent),
            DefaultExtractorsFactory(),
            null, // eventHandler: Handler
            null) // eventListener: ExtractorMediaSource.EventListener

        exoPlayer.prepare(mediaSource)
        exoPlayer.playWhenReady = true
    }
}