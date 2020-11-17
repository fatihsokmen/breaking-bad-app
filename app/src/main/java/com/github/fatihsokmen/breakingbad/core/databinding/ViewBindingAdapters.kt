package com.github.fatihsokmen.breakingbad.core.databinding

import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.github.fatihsokmen.breakingbad.R
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import jp.wasabeef.glide.transformations.BlurTransformation


object ViewBindingAdapters {

    @BindingAdapter("url")
    @JvmStatic
    fun loadImage(view: ImageView, url: String?) {
        url?.let {
            Glide.with(view.context)
                .load(it)
                .into(view)
        }
    }

    @BindingAdapter("blur")
    @JvmStatic
    fun loadBlurImage(view: ImageView, url: String?) {
        url?.let {
            Glide.with(view.context)
                .load(it)
                .apply(RequestOptions.bitmapTransform(BlurTransformation(60, 3)))
                .into(view)
        }
    }

    @BindingAdapter("chips")
    @JvmStatic
    fun setChips(view: ChipGroup, chips: List<String>?) {
        chips?.forEach {
            val chip = Chip(view.context)
            chip.text = it
            view.addView(chip)
        }
    }

    @BindingAdapter("items")
    @JvmStatic
    fun setItems(view: LinearLayout, items: List<String>?) {
        items?.forEach {
            val text = TextView(view.context)
            text.setTextAppearance(R.style.TextAppearance_MaterialComponents_Body1)
            text.text = it
            view.addView(text)
        }
    }

}