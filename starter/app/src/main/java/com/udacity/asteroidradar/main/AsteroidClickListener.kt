package com.udacity.asteroidradar.main

import com.udacity.asteroidradar.model.Asteroid

interface AsteroidClickListener {
    fun onClick(asteroid: Asteroid)
}