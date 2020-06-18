package com.velaphi.tabby.ui.view

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.velaphi.tabby.R
import com.velaphi.tabby.data.database.TabbyEntry
import com.velaphi.tabby.data.model.Image

class MainAdapter: RecyclerView.Adapter<MainAdapter.MainViewHolder>() {

    private var tabbyEntryList: List<TabbyEntry> = arrayListOf()
    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.list_item_cats, parent, false)
        return MainViewHolder(view)
    }

    override fun getItemCount(): Int {
        return tabbyEntryList.size
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val tabbyEntry: TabbyEntry? = tabbyEntryList[position]
        context = holder.itemView.context
        holder.titleTextView.text = tabbyEntry?.title

        Glide
            .with(context)
            .load(tabbyEntry?.url)
            .centerCrop()
            .placeholder(R.drawable.ic_placeholder)
            .error(R.drawable.ic_error)
            .into(holder.catImageView);

        holder.itemView.setOnClickListener {tabbyEntry?.let { openDetailsActivity(tabbyEntry) }  }
    }

    private fun openDetailsActivity(tabbyEntry: TabbyEntry) {
        val intent = Intent(context, TabbyDetailsActivity::class.java)
        intent.putExtra(IMAGE, tabbyEntry)
        context.startActivity(intent)
    }

    fun setItems(tabbyEntryList: List<TabbyEntry>) {
        this.tabbyEntryList = tabbyEntryList
        notifyDataSetChanged()
    }

    class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val catImageView: ImageView = itemView.findViewById(R.id.cat_imageview)
        val titleTextView: TextView = itemView.findViewById(R.id.title_textview)
    }

    companion object {
        const val IMAGE: String = "image"
    }
}
