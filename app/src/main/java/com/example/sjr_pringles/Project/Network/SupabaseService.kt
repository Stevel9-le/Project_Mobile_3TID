package com.example.sjr_pringles.Project.Network

import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface SupabaseApi {

    // 1. REGISTER -> INSERT KE TABEL USERS
    @Headers("Prefer: return=representation")
    @POST("rest/v1/users")
    fun registerUser(@Body user: UserRequest): Call<List<UserRequest>>

    // 2. CRUD TABEL KELUHAN (CUSTOM TOPIK 1)
    @GET("rest/v1/keluhan?select=*&order=id.desc")
    fun getKeluhan(): Call<List<KeluhanItem>>

    @Headers("Prefer: return=representation")
    @POST("rest/v1/keluhan")
    fun insertKeluhan(@Body item: KeluhanItem): Call<List<KeluhanItem>>

    @Headers("Prefer: return=representation")
    @PATCH("rest/v1/keluhan")
    fun updateKeluhan(
        @Query("id") idFilter: String,
        @Body item: KeluhanItem
    ): Call<List<KeluhanItem>>

    @DELETE("rest/v1/keluhan")
    fun deleteKeluhan(
        @Query("id") idFilter: String
    ): Call<Void>

    // 3. CRUD TABEL BOOKING (CUSTOM TOPIK 2)
    @GET("rest/v1/booking?select=*&order=id.desc")
    fun getBooking(): Call<List<BookingItem>>

    @Headers("Prefer: return=representation")
    @POST("rest/v1/booking")
    fun insertBooking(@Body item: BookingItem): Call<List<BookingItem>>

    @Headers("Prefer: return=representation")
    @PATCH("rest/v1/booking")
    fun updateBooking(
        @Query("id") idFilter: String,
        @Body item: BookingItem
    ): Call<List<BookingItem>>

    @DELETE("rest/v1/booking")
    fun deleteBooking(
        @Query("id") idFilter: String
    ): Call<Void>
}

object SupabaseClient {
    private const val SUPABASE_URL = "https://ztsdtpzjpoottrvwmqwr.supabase.co"
    private const val SUPABASE_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Inp0c2R0cHpqcG9vdHRydndtcXdyIiwicm9sZSI6ImFub24iLCJpYXQiOjE3OTU0MzY3NjIsImV4cCI6MjExMTAxMjc2Mn0.9B4skTY4RSgWckfpaW1om6EnFDEQ1zGkCYw9CuL50Nc"

    private val client = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val request = chain.request().newBuilder()
                .addHeader("apikey", SUPABASE_KEY)
                .addHeader("Authorization", "Bearer $SUPABASE_KEY")
                .addHeader("Content-Type", "application/json")
                .build()
            chain.proceed(request)
        }
        .build()

    val api: SupabaseApi by lazy {
        Retrofit.Builder()
            .baseUrl(SUPABASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(SupabaseApi::class.java)
    }
}