package com.velaphi.tabby.ui.view

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.velaphi.tabby.R
import com.velaphi.tabby.data.database.TabbyEntry
import com.velaphi.tabby.ui.view.MainAdapter.Companion.IMAGE
import kotlinx.android.synthetic.main.activity_tabby_details.*

class TabbyDetailsActivity : AppCompatActivity() {
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
}