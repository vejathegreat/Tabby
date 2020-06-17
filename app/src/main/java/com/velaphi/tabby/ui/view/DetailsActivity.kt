package com.velaphi.tabby.ui.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.bumptech.glide.Glide
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.velaphi.tabby.R
import com.velaphi.tabby.data.model.Image
import com.velaphi.tabby.ui.view.MainAdapter.Companion.IMAGE
import kotlinx.android.synthetic.main.activity_details.*


class DetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        setSupportActionBar(findViewById(R.id.toolbar))
        findViewById<CollapsingToolbarLayout>(R.id.toolbar_layout).title = title
        val data = intent.extras
        val image = data?.getParcelable<Image>(IMAGE)

        if (image != null) {
            populateImage(image.url)
            setupSource(image.sourceUrl)
            setupActionBar()
        }


    }

    private fun setupActionBar() {
       val collapsingToolbarLayout = findViewById<CollapsingToolbarLayout>(R.id.toolbar_layout)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        collapsingToolbarLayout.title = "THIS IS MY TITLE"

    }

    private fun setupSource(sourceUrl: String?) {
        val sourceButton = findViewById<Button>(R.id.source_button)
        sourceButton.setOnClickListener {
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(sourceUrl)
            startActivity(i)
        }
    }

    private fun populateImage(url: String) {
        val catImage = findViewById<ImageView>(R.id.imageView)
        Glide
            .with(this)
            .load(url)
            .centerCrop()
            .placeholder(R.drawable.ic_placeholder)
            .error(R.drawable.ic_error)
            .into(catImage)
    }
}