package com.velaphi.tabby.ui.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.velaphi.tabby.R
import com.velaphi.tabby.data.model.Image

class MainAdapter: RecyclerView.Adapter<MainAdapter.MainViewHolder>() {

    private var imageList: List<Image> = arrayListOf()
    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.list_item_cats, parent, false)
        return MainViewHolder(view)
    }

    override fun getItemCount(): Int {
        return imageList.size
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val image: Image? = imageList[position]
        context = holder.itemView.context
        holder.titleTextView.text = context.getString(R.string.image_title, position.toString())

        Glide
            .with(context)
            .load(image?.url)
            .centerCrop()
            .placeholder(R.drawable.ic_placeholder)
            .error(R.drawable.ic_error)
            .into(holder.catImageView);

        holder.itemView.setOnClickListener {image?.let { openDetailsActivity(image) }  }
    }

    private fun openDetailsActivity(image: Image) {
        val intent = Intent(context, DetailsActivity::class.java)
        intent.putExtra(IMAGE, image)
        context.startActivity(intent)
    }

    fun setItems(imageList: List<Image>) {
        this.imageList = imageList
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
