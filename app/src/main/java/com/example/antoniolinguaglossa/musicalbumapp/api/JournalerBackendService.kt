package com.example.antoniolinguaglossa.musicalbumapp.api

import com.example.antoniolinguaglossa.musicalbumapp.model.ResultCont
import retrofit2.Call
import retrofit2.http.*

interface JournalerBackendService {

    companion object {
        fun obtain(): JournalerBackendService {
            return BackendServiceRetrofit
                    .obtain()
                    .create(JournalerBackendService::class.java)
        }
    }

    @POST("authenticate")
    // @POST("user/authenticate")
    fun login(
            @HeaderMap headers: Map<String, String>,
            @Body payload: UserLoginRequest
    ): Call<JournalerApiToken>

    //Uso solo questa...
    @GET("search?")
            // @GET("entity/note")
    fun getResults(@Query("term") artist : String
    ): Call<ResultCont>


    @GET("notes")
    // @GET("entity/note")
    fun getNotes(
            @HeaderMap headers: Map<String, String>
    ): Call<List<String>>

    @GET("todos")
    // @GET("entity/todo")
    fun getTodos(
            @HeaderMap headers: Map<String, String>
    ): Call<List<String>>

    @PUT("entity/note")
    fun publishNotes(
            @HeaderMap headers: Map<String, String>,
            @Body payload: List<String>
    ): Call<Unit>

    @PUT("entity/todo")
    fun publishTodos(
            @HeaderMap headers: Map<String, String>,
            @Body payload: List<String>
    ): Call<Unit>

    @DELETE("entity/note")
    fun removeNotes(
            @HeaderMap headers: Map<String, String>,
            @Body payload: List<String>
    ): Call<Unit>

    @DELETE("entity/todo")
    fun removeTodos(
            @HeaderMap headers: Map<String, String>,
            @Body payload: List<String>
    ): Call<Unit>

}