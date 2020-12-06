package com.udacity.asteroidradar.api

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.udacity.asteroidradar.Constants.API_KEY
import com.udacity.asteroidradar.Constants.API_KEY_QUERY_KEY
import com.udacity.asteroidradar.Constants.BASE_URL
import com.udacity.asteroidradar.model.PictureOfDay
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface NeoService {

    @GET("/neo/rest/v1/feed")
    suspend fun getAsteroidInformation(@QueryMap map: Map<String, String>): String?

    @GET("planetary/apod")
    suspend fun getNasaImage(): PictureOfDay?
}

object AsteroidApi {
    val retrofitService: NeoService by lazy { getRetrofit().create(NeoService::class.java) }
    val retrofitWithMoshiService: NeoService by lazy { getRetrofit(true).create(NeoService::class.java) }
}

val moshi: Moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

fun getRetrofit(withMoshi: Boolean = false): Retrofit {
    val builder = Retrofit.Builder()
        .client(getHttpClient())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .baseUrl(BASE_URL)

    builder.addConverterFactory(
        if (withMoshi) {
            MoshiConverterFactory.create(moshi)
        } else {
            ScalarsConverterFactory.create()
        }
    )

    return builder.build()
}

fun getHttpClient(): OkHttpClient {
    val httpClient = OkHttpClient.Builder()
    httpClient.addInterceptor { chain ->
        val request = chain.request()
        val originalUrl = request.url()
        val url = originalUrl.newBuilder()
            .addEncodedQueryParameter(API_KEY_QUERY_KEY, API_KEY)
            .build()

        val newRequest = request.newBuilder().url(url)
        return@addInterceptor chain.proceed(newRequest.build())
    }

    return httpClient.build()
}