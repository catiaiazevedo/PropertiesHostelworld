package com.example.propertieshostelworld.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize //to pass object of type Property to DetailsFragment using Navigation type safeargs plugin
data class Property(
    val name: String,
    val overallRating: @RawValue Rating,
    val overview: String,
    val lowestPricePerNight: @RawValue Price,
    val imagesGallery: @RawValue List<Image>
): Parcelable
