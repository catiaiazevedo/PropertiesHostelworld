package com.example.propertieshostelworld.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.propertieshostelworld.R
import com.example.propertieshostelworld.model.Location
import com.example.propertieshostelworld.model.Property

class ItemsAdapter : RecyclerView.Adapter<ItemsAdapter.ViewHolder>() {
    private var itemsList: List<Property> = ArrayList()
    private lateinit var location: Location
    private lateinit var itemClickListener: ItemClickListener<Property, Location>

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemName: TextView = itemView.findViewById(R.id.item_name)
        val itemRating: TextView = itemView.findViewById(R.id.item_rating)
        val itemCityCountry: TextView = itemView.findViewById(R.id.item_city_country)
        val itemImage: ImageView = itemView.findViewById(R.id.item_image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = itemsList[position]
        val rating: Double = item.overallRating.overall.toDouble().div(10)
        val imageStr = "https://"+item.imagesGallery[0].prefix+item.imagesGallery[0].suffix

        holder.apply {
            itemName.text = item.name
            itemRating.text = rating.toString().plus("/10")
            itemCityCountry.text = location.city.name.plus(", "+location.city.country)
            itemImage.apply {
                Glide
                    .with(this)
                    .load(imageStr)
                    .placeholder(R.drawable.no_pictures)
                    .into(this)
            }
            itemView.setOnClickListener { itemClickListener.onItemClick(item, location) }
        }
    }

    override fun getItemCount(): Int {
        return itemsList.size
    }

    fun updateItemsList(itemsList: List<Property>) {
        this.itemsList = itemsList
        notifyDataSetChanged()
    }

    fun updateLocation(location: Location) {
        this.location = location
    }

    fun setClickListener(clickListener: ItemClickListener<Property, Location>) {
        itemClickListener = clickListener
    }

    interface ItemClickListener<Property, Location> {
        fun onItemClick(item: Property, location: Location)
    }
}
