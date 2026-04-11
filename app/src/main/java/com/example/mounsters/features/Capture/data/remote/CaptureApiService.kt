package com.example.mounsters.features.capture.data.remote

import retrofit2.http.Body
import retrofit2.http.POST

interface CaptureApiService {

    @POST("captures")
    suspend fun captureMonster(
        @Body request: CaptureRequest
    )

}