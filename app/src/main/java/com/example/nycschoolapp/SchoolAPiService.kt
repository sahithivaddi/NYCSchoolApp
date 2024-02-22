package com.example.nycschoolapp

import retrofit2.http.GET

interface SchoolApiService {
    @GET("s3k6-pzi2.json")
    suspend fun getSchools(): List<SchoolData>
}