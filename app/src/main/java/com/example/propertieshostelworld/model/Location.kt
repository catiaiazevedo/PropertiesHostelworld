package com.example.propertieshostelworld.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize //to pass object of type Location to DetailsFragment using Navigation type safeargs plugin
data class Location(val city: @RawValue City): Parcelable
