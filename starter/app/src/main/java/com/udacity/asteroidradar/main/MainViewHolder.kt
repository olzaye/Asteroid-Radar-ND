package com.udacity.asteroidradar.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.model.Asteroid
import com.udacity.asteroidradar.databinding.AsteroidItemLayoutBinding

class MainViewHolder(private val binding: AsteroidItemLayoutBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Asteroid?, listener: AsteroidClickListener?) {
        item?.let {
            binding.asteroid = it
            listener?.let { binding.listener = it }
            binding.executePendingBindings()
        }
    }

    companion object {
        fun from(parent: ViewGroup): RecyclerView.ViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = AsteroidItemLayoutBinding.inflate(layoutInflater, parent, false)
            return MainViewHolder(binding)
        }
    }
}