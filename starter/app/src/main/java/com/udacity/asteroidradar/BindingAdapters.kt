package com.udacity.asteroidradar

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso
import com.udacity.asteroidradar.model.PictureOfDay

@BindingAdapter("statusIcon")
fun bindAsteroidStatusImage(imageView: ImageView, isHazardous: Boolean) {
    if (isHazardous) {
        imageView.setImageResource(R.drawable.ic_status_potentially_hazardous)
    } else {
        imageView.setImageResource(R.drawable.ic_status_normal)
    }
}

@BindingAdapter("asteroidStatusImage")
fun bindDetailsStatusImage(imageView: ImageView, isHazardous: Boolean) {
    if (isHazardous) {
        imageView.setImageResource(R.drawable.asteroid_hazardous)
    } else {
        imageView.setImageResource(R.drawable.asteroid_safe)
    }
}

@BindingAdapter("astronomicalUnitText")
fun bindTextViewToAstronomicalUnit(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.astronomical_unit_format), number)
}

@BindingAdapter("kmUnitText")
fun bindTextViewToKmUnit(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.km_unit_format), number)
}

@BindingAdapter("velocityText")
fun bindTextViewToDisplayVelocity(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.km_s_unit_format), number)
}

@BindingAdapter("loadUrl")
fun loadUrl(view: ImageView, pictureOfDay: PictureOfDay?) {
    pictureOfDay?.takeIf { it.mediaType == "image" }?.url?.let {
        Picasso.with(view.context).load(it).into(view)
    }
}

@BindingAdapter("dynamicallyContentDescription")
fun setDynamicallyContentDescription(view: ImageView, pictureOfDay: PictureOfDay?) {
    view.contentDescription = pictureOfDay?.title?.takeIf { it.isNotEmpty() }?.let {
        return@let view.context.getString(
            R.string.nasa_picture_of_day_content_description_format,
            it
        )
    } ?: run {
        return@run view.context.getString(R.string.this_is_nasa_s_picture_of_day_showing_nothing_yet)
    }
}



