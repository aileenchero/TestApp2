package com.example.testapp2

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_images.view.*

import com.squareup.picasso.Picasso.with as picassoWith

class ImageAdapter (private var items:List<Item>, private val context: Context):
    RecyclerView.Adapter<ImageAdapter.ViewHolder>() {
        inner class ImageViewHolder(itemView:View): RecyclerView.ViewHolder(itemView)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.item_images,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        picassoWith(holder.itemView.context).load(item.imageUrl).into(holder.itemView.imageView)
    }

    class ViewHolder(view: View):RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.imageView)
    }
}