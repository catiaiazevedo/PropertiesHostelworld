package com.example.propertieshostelworld.ui.views

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.propertieshostelworld.R
import com.example.propertieshostelworld.databinding.FragmentDetailsBinding

class DetailsFragment : Fragment() {
    private val args: DetailsFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentDetailsBinding.inflate(inflater, container, false)

        val property = args.item
        val location = args.location
        val imageStr = "https://"+property.imagesGallery[0].prefix+property.imagesGallery[0].suffix
        val rating: Double = property.overallRating.overall.toDouble().div(10)
        val price = property.lowestPricePerNight.value.toString()+'€'

        binding.apply {
            itemName.text = property.name
            itemCityCountry.text = location.city.name.plus(", "+location.city.country)
            itemRating.text = rating.toString().plus("/10")
            itemPrice.text = price
            itemDescription.text = property.overview
            itemDescription.movementMethod = ScrollingMovementMethod()
            itemImage.apply {
                Glide
                    .with(this)
                    .load(imageStr)
                    .placeholder(R.drawable.no_pictures)
                    .into(this)
            }
        }

        return binding.root
    }
}
