package com.example.gonggangam

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Query

interface AuthRetrofitInterface {
    @GET("authorize")
    fun login(@Query("response_type") response_type: String,
              @Query("client_id") client_id: String,
              @Query("redirect_uri") redirect_uri: String
    ): Call<AuthResponse>

}